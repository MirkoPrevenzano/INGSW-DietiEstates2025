package com.dietiestates.backend;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import com.dietiestates.backend.service.photo.PhotoServiceImpl;
import com.dietiestates.backend.service.photo.storage.FileStorageService;


/**
 * Lista Mock [FileStorageService]
 * 
 * file:
 * CE1: file vuoto ok
 * CE2: file null ok
 * CE3: file no png e jpeg
 * CE4: file senza tipo
 * CE5: nome file null
 * CE6: nome file senza estensione
 * CE7: file valido
 * 
 * folderName:
 * CE1: folderName vuoto
 * CE2: folderName null
 * CE3: folderName non vuoto e null
 */

@ExtendWith(MockitoExtension.class)
@DisplayName("Test per verificare il caricamento di un'immagine")
class UploadPhotoTest {
    @InjectMocks
    PhotoServiceImpl photoServiceImpl;

    @Mock
    FileStorageService fileStorageService;



    private static final String VALID_FOLDER_NAME = "nomeCartella";
    private static final String VALID_FILE_NAME = "nomeFile.png";
    private static final String VALID_FILE_TYPE = "image/png";
    private static final String VALID_FILE_CONTENT = "Contenuto del file di prova";
    private static final String INVALID_FILE_TYPE = "text/txt";
    private static final String INVALID_FILE_NAME = "nomeFile";
   

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

    @Test
    @DisplayName("TC1: caso in cui il contenuto del file è vuoto")
    void emptyFileTest(){
        multipartFile = createMockMultiPartFile(VALID_FILE_NAME, VALID_FILE_TYPE, "");
        assertThrows(IllegalArgumentException.class, () ->{photoServiceImpl.uploadPhoto(multipartFile, folderName);});
        verifyNoInteractions(fileStorageService);
    }

    @Test
    @DisplayName("TC2: caso in cui il file è null")
    void nullFileTest(){
        multipartFile = null;
        assertThrows(IllegalArgumentException.class, () ->{photoServiceImpl.uploadPhoto(multipartFile, folderName);});
        verifyNoInteractions(fileStorageService);
    }

    @Test
    @DisplayName("TC3: caso in cui il contenuto del file è vuoto")
    void noImageFileTest(){
        multipartFile = createMockMultiPartFile(VALID_FILE_NAME, INVALID_FILE_TYPE, VALID_FILE_CONTENT);
        assertThrows(IllegalArgumentException.class, () ->{photoServiceImpl.uploadPhoto(multipartFile, folderName);});
        verifyNoInteractions(fileStorageService);
    }

    @Test
    @DisplayName("TC4: caso in cui il contenuto del file è nullo")
    void emptyTypeFileTest(){
        multipartFile = createMockMultiPartFile(VALID_FILE_NAME, null, VALID_FILE_CONTENT);
        assertThrows(IllegalArgumentException.class, () ->{photoServiceImpl.uploadPhoto(multipartFile, folderName);});
        verifyNoInteractions(fileStorageService);
    }

    @Test
    @DisplayName("TC5: caso in cui il nome del file è nullo")
    void emptyNameFileTest(){
        multipartFile = createMockMultiPartFile(null, VALID_FILE_TYPE, VALID_FILE_CONTENT);
        assertThrows(IllegalArgumentException.class, () ->{photoServiceImpl.uploadPhoto(multipartFile, folderName);});
        verifyNoInteractions(fileStorageService);
    }

    @Test
    @DisplayName("TC6: caso in cui il nome del file è senza estensione")
    void invalidNameFileTest(){
        multipartFile = createMockMultiPartFile(INVALID_FILE_NAME, VALID_FILE_TYPE, VALID_FILE_CONTENT);
        assertThrows(IllegalArgumentException.class, () ->{photoServiceImpl.uploadPhoto(multipartFile, folderName);});
        verifyNoInteractions(fileStorageService);
    }

    @Test
    @DisplayName("TC7: caso in cui il nome della cartella è nullo")
    void nullFolderNameTest(){
        multipartFile = createMockMultiPartFile(VALID_FILE_NAME, VALID_FILE_TYPE, VALID_FILE_CONTENT);
        folderName=null;
        assertThrows(IllegalArgumentException.class, () ->{photoServiceImpl.uploadPhoto(multipartFile, folderName);});
        verifyNoInteractions(fileStorageService);
    } 

    @Test
    @DisplayName("TC8: caso in cui il nome della cartella è vuoto")
    void emptyFolderNameTest(){
        multipartFile = createMockMultiPartFile(VALID_FILE_NAME, VALID_FILE_TYPE, VALID_FILE_CONTENT);
        folderName="";
        assertThrows(IllegalArgumentException.class, () ->{photoServiceImpl.uploadPhoto(multipartFile, folderName);});
        verifyNoInteractions(fileStorageService);
    } 

    @Test
    @DisplayName("TC9: caso valido - file e folderName corretti")
    void validTest(){
        multipartFile = createMockMultiPartFile(VALID_FILE_NAME, VALID_FILE_TYPE, VALID_FILE_CONTENT);
        folderName = VALID_FOLDER_NAME;
        
        // Il test deve verificare che non vengano lanciate eccezioni
        assertDoesNotThrow(() -> {
            String result = photoServiceImpl.uploadPhoto(multipartFile, folderName);
            assertNotNull(result); // Verifica che venga restituita una chiave
            assertTrue(result.startsWith(folderName + "/")); // Verifica il formato della chiave
        });

       verify(fileStorageService, times(1)).uploadFile(
            eq(VALID_FILE_CONTENT.getBytes()),
            anyString(),           
            eq(VALID_FILE_TYPE),  
            anyString(),          
            any()                 
        );
    }




}
