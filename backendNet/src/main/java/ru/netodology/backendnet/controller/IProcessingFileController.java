package ru.netodology.backendnet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.netodology.backendnet.dto.FileRenameRequestDto;
import ru.netodology.backendnet.dto.FileRs;
import ru.netodology.backendnet.dto.ResponseDto;

import java.util.List;

public interface IProcessingFileController {

    ResponseEntity<? extends ResponseDto> uploadFile(String filename, MultipartFile file, Integer userId);


    ResponseEntity<? extends ResponseDto> deleteFile(String filename, Integer userId);


    ResponseEntity<byte[]> downloadFile(String filename, Integer userId);


    ResponseEntity<? extends ResponseDto> renameFile(String currentFilename, FileRenameRequestDto request,
                                                     Integer userId);

    ResponseEntity<List<FileRs>> getAllFiles(Integer limit, Integer userId);

}
