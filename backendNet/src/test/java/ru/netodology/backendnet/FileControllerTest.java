package ru.netodology.backendnet;

import static org.junit.jupiter.api.Assertions.assertEquals;

//public class FileControllerTest {
//
//    @InjectMocks
//    private FileControllerImpl fileController;
//
//    @Mock
//    private FileServiceImpl fileService;
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
