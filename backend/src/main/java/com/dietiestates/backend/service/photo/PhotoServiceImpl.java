
package com.dietiestates.backend.service.photo;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ContentDisposition;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dietiestates.backend.service.photo.storage.FileStorageService;

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
        if (contentType == null || (!contentType.equals("image/jpeg") && !contentType.equals("image/png"))) 
        {
            log.error("Invalid or unsupported photo format: {}", contentType);
            throw new IllegalArgumentException("Photo format is invalid or not supported: must be JPEG or PNG");
        }

        if(folderName == null || folderName.isBlank())
        {
            log.error("Folder name for photo is not valid!");
            throw new IllegalArgumentException("Folder name for photo is not valid!");   
        }

        String contentDisposition = ContentDisposition.inline().build().toString();

        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFilename != null && originalFilename.contains(".")) 
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        else
            throw new IllegalArgumentException("Original filename is not valid: " + originalFilename);
        
        String uniqueFilename = UUID.randomUUID().toString() + fileExtension;
        String photoKey = folderName + "/" + uniqueFilename;

        Map<String,String> photoMetadata = new HashMap<>();
        photoMetadata.put("originalFilename", originalFilename);

        fileStorageService.uploadFile(file.getBytes(), photoKey, contentType, contentDisposition, photoMetadata);

        return photoKey;        
    }

    
    @Override
    public PhotoResult<byte[]> getPhotoAsByteArray(String photokey) 
    {
        byte[] photoBytes = fileStorageService.getFile(photokey);

        Map<String, String> metadata = fileStorageService.getFileMetadata(photokey);
        String contentType = metadata.getOrDefault("ContentType", "application/octet-stream"); 

        return new PhotoResult<>(photoBytes, contentType);
    }


    @Override
    public PhotoResult<String> getPhotoAsBase64(String photoKey) 
    {   
        byte[] photoBytes = fileStorageService.getFile(photoKey);

        Map<String, String> metadata = fileStorageService.getFileMetadata(photoKey);
        String contentType = metadata.getOrDefault("ContentType", "application/octet-stream"); 

        String photoBase64 = Base64.getEncoder().encodeToString(photoBytes);

        return new PhotoResult<>(photoBase64, contentType);
    }


    @Override
    public String getPhotoPublicUrl(String photoKey) 
    {
        return fileStorageService.getFilePublicUrl(photoKey);
    }


    @Override
    public void deletePhoto(String photoKey) 
    {
        fileStorageService.deleteFile(photoKey);
    }
}