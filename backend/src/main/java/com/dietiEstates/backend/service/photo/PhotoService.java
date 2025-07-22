
package com.dietiEstates.backend.service.photo;

import org.springframework.web.multipart.MultipartFile;

import com.dietiEstates.backend.model.entity.Photo;


public interface PhotoService
{
    public String uploadPhoto(String folderName, MultipartFile file);
    public byte[] getPhoto(Photo photo);
    public void deletePhoto(String photKey);
}
