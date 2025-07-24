package com.dietiEstates.backend.service.photo.storage;

import java.io.IOException;
import java.util.Map;


public interface FileStorageService 
{
    void uploadFile(byte[] file, String photoKey, String contentType, String contentDisposition, Map<String, String> photoMetadata) throws IOException;

    byte[] getFile(String photoKey) throws IOException;
    
    String getFilePublicUrl(String photoKey) throws IOException;

    Map<String, String> getFileMetadata(String photoKey) throws IOException;

    void deleteFile(String photoKey) throws IOException;
}