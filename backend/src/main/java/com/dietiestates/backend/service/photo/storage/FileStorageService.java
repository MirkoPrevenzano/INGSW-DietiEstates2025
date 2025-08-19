
package com.dietiestates.backend.service.photo.storage;

import java.util.Map;


public interface FileStorageService 
{
    void uploadFile(byte[] fileBytes, String fileStorageKey, String contentType, String contentDisposition, Map<String, String> photoMetadata);

    byte[] getFile(String fileStorageKey);
    
    String getFilePublicUrl(String fileStorageKey);

    Map<String, String> getFileMetadata(String fileStorageKey);

    void deleteFile(String fileStorageKey);
}