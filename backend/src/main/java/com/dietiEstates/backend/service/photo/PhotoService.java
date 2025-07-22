
package com.dietiEstates.backend.service.photo;

import org.springframework.web.multipart.MultipartFile;

import com.dietiEstates.backend.model.entity.Photo;


public interface PhotoService
{
    public void uploadPhoto(MultipartFile file);
    public byte[] getPhoto(Photo photo);
}
