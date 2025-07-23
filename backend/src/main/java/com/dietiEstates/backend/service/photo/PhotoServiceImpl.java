
package com.dietiEstates.backend.service.photo;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dietiEstates.backend.model.entity.Photo;
import com.dietiEstates.backend.service.photo.storage.FileStorageService;
import com.dietiEstates.backend.util.AmazonS3Util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.exception.SdkException;


@Service
@RequiredArgsConstructor
@Slf4j
public class PhotoServiceImpl implements PhotoService
{
    private final FileStorageService fileStorageService;



    @Override
    public String uploadPhoto(MultipartFile file, String folderName) throws IOException
    {
        if (file == null || file.isEmpty()) 
            throw new IllegalArgumentException("Il file da caricare non può essere null o vuoto.");
        
        if (folderName == null || folderName.trim().isEmpty()) 
            throw new IllegalArgumentException("Il nome della cartella S3 non può essere null o vuoto.");
        

        String contentType = file.getContentType();
        Long contentLength = file.getSize();
        
        log.info("file content type: {}", contentType);
        log.info("file size: {}", contentLength);
    
        if(contentType != null && !contentType.equals("image/jpeg") && !contentType.equals("image/png"))
        {
            log.error("Photo format is not supported!");
            throw new IllegalArgumentException("Photo format is not supported!");
        }
        if(contentLength >= 10000000)
        {
            log.error("Photo have exceeded the maximum size!");
            throw new IllegalArgumentException("Photo have exceeded the maximum size!");
        }

        Map<String,Object> photoMetadata = new HashMap<>();
        photoMetadata.put("contentType", contentType);
        photoMetadata.put("contentLength", contentLength);
        photoMetadata.put("contentDisposition", "inline");

        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFilename != null && originalFilename.contains(".")) 
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
        String photoKey = folderName + "/" + uniqueFileName;

        fileStorageService.uploadFile(file.getBytes(), "%s".formatted(photoKey), photoMetadata);

        return photoKey;        
    }


    @Override
    public String getPhotoAsBase64(String photoKey) throws IOException {
        if (photoKey == null || photoKey.trim().isEmpty()) {
            throw new IllegalArgumentException("La chiave della foto non può essere nulla o vuota.");
        }
        
        // 1. Scarica i byte della foto dal servizio di storage
        byte[] photoBytes = fileStorageService.downloadFile(photoKey);

        // 2. Recupera i metadati (per ottenere il Content-Type)
        Map<String, Object> metadata = fileStorageService.getFileMetadata(photoKey);
        String contentType = (String) metadata.getOrDefault("ContentType", "application/octet-stream"); 

        // 3. Converti i byte in stringa Base64 (logica specifica di PhotoService)
        String base64String = Base64.getEncoder().encodeToString(photoBytes);

        //return new PhotoData(base64String, contentType);

        return base64String;
    }


    @Override
    public String getPhotoUrl(String photoKey) throws IOException 
    {
        return fileStorageService.getFilePublicUrl(photoKey);
    }


    @Override
    public void deletePhoto(String photoKey) throws IOException 
    {
        fileStorageService.deleteFile(photoKey);
    }
}