package ru.netodology.backendnet.service;

import org.springframework.web.multipart.MultipartFile;
import ru.netodology.backendnet.dto.FileRenameRequestDto;
import ru.netodology.backendnet.dto.FileRs;
import ru.netodology.backendnet.dto.ResponseDto;

import java.util.List;

public interface IFile {
    ResponseDto saveFile(MultipartFile file, String filename, Integer userId);

    List<FileRs> getAllFiles(Integer limit, Integer userId);

    ResponseDto renameFile(String currentFilename, FileRenameRequestDto newFilename, Integer userId);

    ResponseDto deleteFile(String filename, Integer userId);

    byte[] getFileByFilename(String filename, Integer userId);
}

