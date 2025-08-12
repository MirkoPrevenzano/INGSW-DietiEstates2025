
package com.dietiEstates.backend.exception;

import com.dietiEstates.backend.enums.FileStorageOperation;
import com.dietiEstates.backend.enums.FileStorageProvider;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class FileStorageServiceException extends RuntimeException
{
    private final String fileKey;              
    private final Long fileSize;
    private final FileStorageProvider fileStorageProvider; 
    private final FileStorageOperation fileStorageOperation;    


    public FileStorageServiceException(String msg)
    {
        this(msg, new String(""), null, null, null, null);
    }
    
    public FileStorageServiceException(String msg, Throwable cause)
    {
        this(msg, new String(""), null, null, null, cause);
    }

    public FileStorageServiceException(String fileKey, Long fileSize, FileStorageProvider fileStorageProvider, FileStorageOperation fileStorageOperation)
    {
        this(null, fileKey, fileSize, fileStorageProvider, fileStorageOperation, null);
    }

    public FileStorageServiceException(String fileKey, Long fileSize, FileStorageProvider fileStorageProvider, FileStorageOperation fileStorageOperation, Throwable cause)
    {
        this(null, fileKey, fileSize, fileStorageProvider, fileStorageOperation, cause);
    }

    public FileStorageServiceException(String msg, String fileKey, Long fileSize, FileStorageProvider fileStorageProvider, FileStorageOperation fileStorageOperation)
    {
        this(msg, fileKey, fileSize, fileStorageProvider, fileStorageOperation, null);
    }

    public FileStorageServiceException(String msg, String fileKey, Long fileSize, FileStorageProvider fileStorageProvider, FileStorageOperation fileStorageOperation, Throwable cause)
    {
        super(msg, cause);
        this.fileKey = fileKey;
        this.fileSize = fileSize;
        this.fileStorageProvider = fileStorageProvider;
        this.fileStorageOperation = fileStorageOperation;
    }


    @Override
    public String getMessage() 
    {
        String baseMessage = super.getMessage();
        StringBuilder fullMessage = new StringBuilder();
        
        if (baseMessage != null && !baseMessage.isEmpty()) 
            fullMessage.append(baseMessage);
        else 
            fullMessage.append("Errore durante il file storing!");

        
        if (fileKey != null) 
            fullMessage.append("\nFile key: ").append(fileKey);
        
        if (fileSize != null)
            fullMessage.append("\nFile size: ").append(fileSize);
        
        if (fileStorageProvider != null)
            fullMessage.append("\nFile storage provider: ").append(fileStorageProvider);

        if (fileStorageOperation != null)
            fullMessage.append("\nFile storage operation: ").append(fileStorageOperation);
        

        return fullMessage.toString();
    }
}