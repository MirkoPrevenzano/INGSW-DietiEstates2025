
package com.dietiEstates.backend.service.photo;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.dietiEstates.backend.model.entity.Photo;
import com.dietiEstates.backend.util.AmazonS3Util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.exception.SdkException;


@Service
@RequiredArgsConstructor
@Slf4j
public class PhotoServiceAmazonS3Impl implements PhotoService
{
    private final AmazonS3Util amazonS3Util;

    @Override
    public String uploadPhoto(String folderName, MultipartFile file) 
    {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Il file da caricare non può essere null o vuoto.");
        }
        if (folderName == null || folderName.trim().isEmpty()) {
            throw new IllegalArgumentException("Il nome della cartella S3 non può essere null o vuoto.");
        }
        String contentType = file.getContentType();
        Long size = file.getSize();
    
        log.info("file content type: {}", contentType);
        log.info("file size: {}", size);
    
        if(contentType != null && !contentType.equals("image/jpeg") && !contentType.equals("image/png"))
        {
            log.error("Photo format is not supported!");
            throw new IllegalArgumentException("Photo format is not supported!");
        }
        if(size >= 10000000)
        {
            log.error("Photo have exceeded the maximum size!");
            throw new IllegalArgumentException("Photo have exceeded the maximum size!");
        }

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType()); // Salva il Content-Type nel metadata S3

        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
        String photoKey = folderName + "/" + uniqueFileName;
        try 
        {
            amazonS3Util.putObject("%s".formatted(photoKey), file.getBytes());
        } 
        catch (SdkException | IOException e) 
        {
            log.error("Amazon S3/SDK/IO exception has occurred while putting photo in the bucket!" + e.getMessage());
            throw new RuntimeException("Failed to upload photo!", e);
        }

        return photoKey;        
    }


    @Override
    public byte[] getPhoto(Photo photo) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public void deletePhoto(String photKey) {
        // TODO Auto-generated method stub
        
    }
}