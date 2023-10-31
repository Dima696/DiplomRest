package ru.netodology.backendnet.service;

import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.netodology.backendnet.dto.FileRenameRequestDto;
import ru.netodology.backendnet.dto.FileRs;
import ru.netodology.backendnet.dto.ResponseDto;
import ru.netodology.backendnet.exception.FileNotFoundException;
import ru.netodology.backendnet.exception.FileOperationException;
import ru.netodology.backendnet.exception.UserNotFoundException;
import ru.netodology.backendnet.model.User;
import ru.netodology.backendnet.model.UserFile;
import ru.netodology.backendnet.repository.FileRepository;
import ru.netodology.backendnet.repository.UserRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final UserRepository userRepository;
    private final FileRepository fileRepository;

    @Override
    @Transactional
    public ResponseDto saveFile(Resource file, String filename, Integer userId) {
        User user = findUserById(userId);
        checkExistFileByIdAndUser(filename);
        try {
            byte[] content = getContentFile(file);
            UserFile userFile = UserFile.builder()
                    .filename(filename)
                    .user(user)
                    .content(content)
                    .size(file.contentLength())
                    .lastModifyingDateTime(LocalDateTime.now())
                    .build();
            fileRepository.saveAndFlush(userFile);

            return new ResponseDto("File successfully save");

        } catch (DataIntegrityViolationException ex) {
            throw new FileOperationException("Error of saving file");
        } catch (IOException ex) {
            throw new RuntimeException("Error of reading file");
        }
    }

    @Override
    public List<FileRs> getAllFiles(Integer limit, Integer userId) {
        checkExistUserById(userId);
        List<FileRs> files = getFilesByUserId(userId, limit,
                Sort.by(Sort.Direction.DESC, "lastModifyingDateTime"));

        return files.isEmpty()
                ? Collections.emptyList()
                : files.stream()
                .limit(limit == null ? files.size() : limit)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ResponseDto renameFile(String currentFilename, FileRenameRequestDto newFilename, Integer userId) {
        checkExistUserById(userId);
        UserFile userFile = findFileAndGetIfExist(currentFilename, userId);

        userFile.setFilename(newFilename.filename());
        userFile.setLastModifyingDateTime(LocalDateTime.now());
        fileRepository.saveAndFlush(userFile);

        return new ResponseDto("File renamed successfully.");
    }

    @Override
    @Transactional
    public ResponseDto deleteFile(String filename, Integer userId) {
        checkExistUserById(userId);
        if (fileRepository.existsByFilenameAndUserId(filename, userId)) {
            fileRepository.deleteByFilename(filename);
            return new ResponseDto("File '%s' was successfully deleted".formatted(filename));
        } else {
            throw new FileNotFoundException("File '%s' not found".formatted(filename));
        }
    }

    @Override
    @Transactional
    public Resource getFileByFilename(String filename, Integer userId) {
        UserFile userFile = findFileAndGetIfExist(filename, userId);
        return new ByteArrayResource(userFile.getContent());
    }

    private void checkExistFileByIdAndUser(String filename) {
        if (fileRepository.existsByFilename(filename)) {
            throw new FileOperationException("File with filename ='%s' already exist".formatted(filename));
        }
    }

    private UserFile findFileAndGetIfExist(String currentFilename, Integer userId) {
        return fileRepository.findByUserIdAndFilename(userId, currentFilename)
                .orElseThrow(() ->
                        new FileNotFoundException("File with name '%s' not found".formatted(currentFilename)));
    }

    private User findUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id='%s' not found".formatted(id)));
    }

    private void checkExistUserById(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User with id='%s' not exist".formatted(id));
        }
    }

    private byte[] getContentFile(Resource resource) throws IOException {
        String originalFilename = resource.getFilename();
        if (Objects.nonNull(originalFilename) && originalFilename.endsWith("pdf")) {
            return readFilePDF(resource);
        }
        return resource.getInputStream().readAllBytes();
    }

    private byte[] readFilePDF(Resource resource) {
        try (PDDocument document = PDDocument.load(resource.getInputStream())) {
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            return pdfTextStripper.getText(document).getBytes();
        } catch (IOException e) {
            throw new FileOperationException("Error of reading file");
        }
    }

    private List<FileRs> getFilesByUserId(Integer userId, Integer limit, Sort sort) {
        Pageable pageable = PageRequest.of(0, limit, sort);
        return fileRepository.findByUserId(userId, pageable);
    }
}

