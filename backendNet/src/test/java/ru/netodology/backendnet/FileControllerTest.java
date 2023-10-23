package ru.netodology.backendnet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import ru.netodology.backendnet.controller.impl.FileController;
import ru.netodology.backendnet.service.imp.FileService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

//public class FileControllerTest {
//
//    @InjectMocks
//    private FileController fileController;
//
//    @Mock
//    private FileService fileService;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testUploadFile() {
//        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
//                "text/plain", "file-content".getBytes());
//
//        ResponseEntity<?> response = fileController.uploadFile( "test.txt", multipartFile, 1);
//
//        assertEquals(201, response.getStatusCodeValue());
//        verify(fileService, times(1)).saveFile(any(), anyString(), anyInt());
//    }
//    @Test
//    public void testDeleteFile() {
//        ResponseEntity<?> response = fileController.deleteFile("token123", 1);
//
//        assertEquals(200, response.getStatusCodeValue());
//        verify(fileService, times(1)).deleteFile(anyString(), anyInt());
//    }
//    @Test
//    public void testDownloadFile() throws Exception {
//        byte[] fileData = "file-content".getBytes();
//        when(fileService.getFileByFilename(anyString(), anyInt())).thenReturn(fileData);
//
//        ResponseEntity<byte[]> response = fileController.downloadFile("token123", 1);
//
//        assertEquals(200, response.getStatusCodeValue());
//        assertEquals(fileData, response.getBody());
//    }
//
//}
