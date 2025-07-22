
package com.dietiEstates.backend.service.photo.storage;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;


@Service
@RequiredArgsConstructor
@Slf4j
public class FileStorageServiceAmazonS3Impl implements FileStorageService
{
    private final S3Client s3Client;

	@Value("${aws.bucket.name}")
	private String bucketName;


    @Override
    public void uploadFile(byte[] file, String photoKey, Map<String, Object> photoMetadata) throws IOException 
    {

		PutObjectRequest putObjectRequest = PutObjectRequest.builder()
															.bucket(bucketName)
															.key(photoKey)
                                                            .contentType((String) photoMetadata.get("contentType"))
                                                            .contentLength((Long) photoMetadata.get("contentLength"))
                                                            .contentDisposition((String) photoMetadata.get("contentDisposition"))
                                                            //.metadata(photoMetadata)
															.build();		

		try 
		{
			s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file));
			log.info("File was uploaded successfully in the AmazonS3 bucket");
		} 
		catch (SdkException e)
		{
            log.error("Amazon S3/SDK exception has occurred while uploading file in the AmazonS3 bucket!");
            throw e;	
		}        
    }


    @Override
    public void deleteFile(String photoKey) throws IOException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public byte[] downloadFile(String photoKey) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, String> getFileMetadata(String photoKey) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getFilePublicUrl(String photoKey) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }
    
    
}
