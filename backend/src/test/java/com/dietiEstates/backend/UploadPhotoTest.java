package com.dietiEstates.backend;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.dietiestates.backend.service.photo.PhotoServiceImpl;
import com.dietiestates.backend.service.photo.storage.FileStorageService;


/**
 * Lista Mock [FileStorageService]
 * 
 * file:
 * CE1: file vuoto
 * CE2: file null
 * CE3: file no png e jpeg
 * CE4: file senza tipo
 * CE5: nome file null
 * CE6: nome file senza . 
 * 
 * folderName:
 * CE1: folderName vuoto
 * CE2: folderName null
 * CE3: folderName non vuoto e null
 */

@ExtendWith(MockitoExtension.class)
@DisplayName("Test per verificare il caricamento di un'immagine")
public class UploadPhotoTest {
    @InjectMocks
    PhotoServiceImpl photoServiceImpl;

    @Mock
    FileStorageService fileStorageService;



    private static final String VALID_FOLDER_NAME = "nomeCartella";
    private static final String VALID_FILE_NAME = "nomeFile.png";
    private static final String VALID_FILE_TYPE = "image/png";
    private static final String VALID_FILE_CONTENT = "Contenuto del file di prova";
   

    MockMultipartFile multipartFile;
    String folderName;
    @BeforeEach
    void setUp(){
        folderName = VALID_FOLDER_NAME;

    }


    private MockMultipartFile createMockMultiPartFile(String fileName, String type, String content) {
        return new MockMultipartFile(
            "file",            // nome del parametro
            fileName,               // nome originale del file
            type,                   // content type
            content.getBytes()      // contenuto del file
        );
    }

    //CE1: file vuoto
    @Test
    @DisplayName("TC1: caso in cui il contenuto del file è vuoto")
    void emptyFileTest(){
        multipartFile = createMockMultiPartFile(VALID_FILE_NAME, VALID_FILE_TYPE, "");
        assertThrows(IllegalArgumentException.class, () ->{photoServiceImpl.uploadPhoto(multipartFile, folderName);});
        verifyNoInteractions(fileStorageService);
    }

}
