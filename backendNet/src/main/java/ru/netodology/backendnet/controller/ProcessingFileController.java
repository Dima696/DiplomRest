package ru.netodology.backendnet.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.netodology.backendnet.dto.FileRenameRequestDto;
import ru.netodology.backendnet.dto.FileRs;
import ru.netodology.backendnet.dto.ResponseDto;

import java.util.List;

public interface ProcessingFileController {

    ResponseEntity<? extends ResponseDto> uploadFile(String filename, MultipartFile file, HttpServletRequest request);


    ResponseEntity<? extends ResponseDto> deleteFile(String filename, HttpServletRequest request);


    ResponseEntity<Resource> downloadFile(String filename, HttpServletRequest request);


    ResponseEntity<? extends ResponseDto> renameFile(String currentFilename, FileRenameRequestDto request,
                                                     HttpServletRequest httpServletRequest);

    ResponseEntity<List<FileRs>> getAllFiles(Integer limit, HttpServletRequest request);

}
