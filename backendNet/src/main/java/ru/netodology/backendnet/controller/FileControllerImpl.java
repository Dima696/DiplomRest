package ru.netodology.backendnet.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netodology.backendnet.dto.FileRenameRequestDto;
import ru.netodology.backendnet.dto.FileRs;
import ru.netodology.backendnet.dto.ResponseDto;
import ru.netodology.backendnet.service.AuthService;
import ru.netodology.backendnet.service.FileService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileControllerImpl implements ProcessingFileController {
    private final FileService fileService;
    private final AuthService authService;

    @Override
    @PostMapping
    public ResponseEntity<ResponseDto> uploadFile(
            @RequestParam("filename") String filename,
            @RequestPart("file") @NotNull MultipartFile file, HttpServletRequest request) {
        Integer userId = authService.getUserIdByLogin(request.getRemoteUser());
        log.info("Request for upload file filename={} by user with id={}", filename, userId);
        ResponseDto result = fileService.saveFile(file.getResource(), filename, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }


    @Override
    @DeleteMapping
    public ResponseEntity<ResponseDto> deleteFile(
            @RequestParam("filename") String filename, HttpServletRequest request) {
        Integer userId = authService.getUserIdByLogin(request.getRemoteUser());
        log.info("Request for delete file with filename={} by user with id={}", filename, userId);
        ResponseDto result = fileService.deleteFile(filename, userId);
        return ResponseEntity.ok(result);
    }

    @Override
    @GetMapping
    public ResponseEntity<Resource> downloadFile(
            @RequestParam("filename") String filename, HttpServletRequest request) {
        Integer userId = authService.getUserIdByLogin(request.getRemoteUser());
        log.info("Request for download file by user with id={}: filename={}", userId, filename);
        Resource resource = fileService.getFileByFilename(filename, userId);
        return ResponseEntity.ok(resource);
    }

    @Override
    @PutMapping
    public ResponseEntity<ResponseDto> renameFile(
            @RequestParam("filename") String currentFilename,
            @RequestBody FileRenameRequestDto request, HttpServletRequest httpServletRequest) {
        Integer userId = authService.getUserIdByLogin(httpServletRequest.getRemoteUser());
        log.info("Request for rename file by user with id={}: filename={}, new filename={}",
                userId, currentFilename, request.filename());
        ResponseDto result = fileService.renameFile(currentFilename, request, userId);
        return ResponseEntity.ok(result);
    }

    @Override
    @GetMapping("/list")
    public ResponseEntity<List<FileRs>> getAllFiles(
            @RequestParam(value = "limit", required = false, defaultValue = "100") Integer limit,
            HttpServletRequest request) {
        Integer userId = authService.getUserIdByLogin(request.getRemoteUser());
        log.info("Request for get all files by user with id={}", userId);
        List<FileRs> files = fileService.getAllFiles(limit, userId);
        return ResponseEntity.ok(files);
    }
}
