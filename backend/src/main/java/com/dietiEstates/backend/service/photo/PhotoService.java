
package com.dietiEstates.backend.service.photo;

import org.springframework.web.multipart.MultipartFile;


public interface PhotoService
{
    public String uploadPhoto(MultipartFile file, String folderName);
    public byte[] getPhoto(String photoKey);
    public void deletePhoto(String photKey);
}
