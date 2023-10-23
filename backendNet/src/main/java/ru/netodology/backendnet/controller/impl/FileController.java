package ru.netodology.backendnet.controller.impl;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netodology.backendnet.controller.IProcessingFileController;
import ru.netodology.backendnet.dto.FileRenameRequestDto;
import ru.netodology.backendnet.dto.FileRs;
import ru.netodology.backendnet.dto.ResponseDto;
import ru.netodology.backendnet.service.imp.FileService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController implements IProcessingFileController {
    private final FileService fileService;

    @Override
    @PostMapping("/{userId}")
    public ResponseEntity<? extends ResponseDto> uploadFile(
            @RequestParam("filename") String filename,
            @RequestPart("file") @NotNull MultipartFile file, @PathVariable Integer userId) {
        log.info("Request for upload file filename={} by user with id={}", filename, userId);
        ResponseDto result = fileService.saveFile(file, filename, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Override
    @DeleteMapping("/{userId}")
    public ResponseEntity<? extends ResponseDto> deleteFile(
            @RequestParam("filename") String filename,
            @PathVariable Integer userId) {
        log.info("Request for delete file with filename={} by user with id={}", filename, userId);
        ResponseDto result = fileService.deleteFile(filename, userId);
        return ResponseEntity.ok(result);
    }

    @Override
    @GetMapping("/{userId}")
    public ResponseEntity<byte[]> downloadFile(
            @RequestParam("filename") String filename,
            @PathVariable Integer userId) {
        log.info("Request for download file by user with id={}: filename={}", userId, filename);
        byte[] file = fileService.getFileByFilename(filename, userId);
        return ResponseEntity.ok(file);
    }

    @Override
    @PutMapping("/{userId}")
    public ResponseEntity<? extends ResponseDto> renameFile(
            @RequestParam("filename") String currentFilename,
            @RequestBody FileRenameRequestDto request, @PathVariable Integer userId) {
        log.info("Request for rename file by user with id={}: filename={}, new filename={}",
                userId, currentFilename, request.filename());
        ResponseDto result = fileService.renameFile(currentFilename, request, userId);
        return ResponseEntity.ok(result);
    }

    @Override
    @GetMapping("/{userId}/list")
    public ResponseEntity<List<FileRs>> getAllFiles(
            @RequestParam(value = "limit", required = false, defaultValue = "100") Integer limit,
            @PathVariable Integer userId) {
        log.info("Request for get all files by user with id={}", userId);
        List<FileRs> files = fileService.getAllFiles(limit, userId);
        return ResponseEntity.ok(files);
    }
}
