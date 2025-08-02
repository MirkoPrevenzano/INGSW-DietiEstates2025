
package com.dietiEstates.backend.service.photo;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dietiEstates.backend.service.photo.storage.FileStorageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


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
        
        String contentType = file.getContentType();
        if(contentType != null && !contentType.equals("image/jpeg") && !contentType.equals("image/png"))
        {
            log.error("Photo format is not supported!");
            throw new IllegalArgumentException("Photo format is not supported: " + contentType);
        }

        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFilename != null && originalFilename.contains(".")) 
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        
        String uniqueFilename = UUID.randomUUID().toString() + fileExtension;
        String photoKey = folderName + "/" + uniqueFilename;

        Map<String,String> photoMetadata = new HashMap<>();
        photoMetadata.put("originalFilename", originalFilename);

        fileStorageService.uploadFile(file.getBytes(), photoKey, contentType, "inline", photoMetadata);

        return photoKey;        
    }

    @Override
    public String getPhotoAsBase64(String photoKey) throws IOException 
    {
        if (photoKey == null || photoKey.trim().isEmpty()) {
            throw new IllegalArgumentException("La chiave della foto non può essere nulla o vuota.");
        }
        
        byte[] photoBytes = fileStorageService.getFile(photoKey);

        Map<String, String> metadata = fileStorageService.getFileMetadata(photoKey);
        String contentType = (String) metadata.getOrDefault("ContentType", "application/octet-stream"); 

        String base64String = Base64.getEncoder().encodeToString(photoBytes);

        //return new PhotoData(base64String, contentType);

        return base64String;
    }


    @Override
    public String getPhotoPublicUrl(String photoKey) throws IOException 
    {
        return fileStorageService.getFilePublicUrl(photoKey);
    }


    @Override
    public void deletePhoto(String photoKey) throws IOException 
    {
        fileStorageService.deleteFile(photoKey);
    }
}