
package com.dietiEstates.backend.service.photo.storage;

import java.util.Map;


public interface FileStorageService 
{
    void uploadFile(byte[] file, String photoKey, String contentType, String contentDisposition, Map<String, String> photoMetadata);

    byte[] getFile(String photoKey);
    
    String getFilePublicUrl(String photoKey);

    Map<String, String> getFileMetadata(String photoKey);

    void deleteFile(String photoKey);
}