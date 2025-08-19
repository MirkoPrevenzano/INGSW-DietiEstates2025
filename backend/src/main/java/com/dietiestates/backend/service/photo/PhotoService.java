
package com.dietiestates.backend.service.photo;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;


public interface PhotoService
{
    public String uploadPhoto(MultipartFile file, String folderName) throws IOException;
    public PhotoResult<String> getPhotoAsBase64(String photoKey);
    public PhotoResult<byte[]> getPhotoAsByteArray(String photokey);
    public String getPhotoPublicUrl(String photoKey);
    public void deletePhoto(String photKey);
}