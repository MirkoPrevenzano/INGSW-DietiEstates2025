
package com.dietiEstates.backend.service.photo;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;


public interface PhotoService
{
    public String uploadPhoto(MultipartFile file, String folderName) throws IOException;
    public String getPhotoAsBase64(String photoKey) throws IOException;
    public String getPhotoUrl(String photoKey) throws IOException;
    public void deletePhoto(String photKey) throws IOException;
}