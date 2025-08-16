
package com.dietiEstates.backend.service.photo.storage;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dietiEstates.backend.enums.FileStorageOperation;
import com.dietiEstates.backend.enums.FileStorageProvider;
import com.dietiEstates.backend.exception.FileStorageServiceException;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class FileStorageServiceAmazonS3Impl implements FileStorageService
{
    private final S3Client s3Client;

    private final static FileStorageProvider PROVIDER = FileStorageProvider.AMAZON_S3;

	@Value("${aws.bucket.name}")
	private String bucketName;


    @Override
    public void uploadFile(byte[] fileBytes, String fileStorageKey, String contentType, String contentDisposition, Map<String, String> photoMetadata) 
    {
		PutObjectRequest putObjectRequest = PutObjectRequest.builder()
															.bucket(bucketName)
 															.key(fileStorageKey)
                                                            .contentType(contentType)
                                                            .contentDisposition(contentDisposition)
                                                            .metadata(photoMetadata)
															.build();		

		try 
		{
			s3Client.putObject(putObjectRequest, RequestBody.fromBytes(fileBytes));
            log.info("File with key '{}' was uploaded successfully in Amazon S3 bucket '{}'.", fileStorageKey, bucketName);
		} 
		catch (SdkException e)
		{
            log.error("Amazon S3/SDK exception occurred while uploading file with key '{}' in bucket '{}': {}", fileStorageKey, bucketName, e.getMessage());
            throw new FileStorageServiceException("Failed to upload file in storage", fileStorageKey, (long) fileBytes.length, PROVIDER, FileStorageOperation.UPLOAD, e);
		}        
    }


    @Override
    public byte[] getFile(String fileStorageKey) 
    {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                                                            .bucket(bucketName)
                                                            .key(fileStorageKey)
                                                            .build();		

        try 
        {
/*             ResponseInputStream<GetObjectResponse> responseInputStream = s3Client.getObject(getObjectRequest);
            return responseInputStream.readAllBytes(); */
            ResponseBytes<GetObjectResponse> objectBytesAndResponse = s3Client.getObjectAsBytes(getObjectRequest);
            log.info("File with key '{}' was retrieved successfully from the Amazon S3 bucket '{}'", fileStorageKey, bucketName);
            return objectBytesAndResponse.asByteArray();
        } 
        catch (NoSuchKeyException e) 
        {
            log.error("File with key '{}' not found in Amazon S3 bucket '{}'.", fileStorageKey, bucketName);
            throw new FileStorageServiceException("File not found in storage", fileStorageKey, null, PROVIDER, FileStorageOperation.READ, e);

        } catch (SdkException e) {
            log.error("Amazon S3/SDK exception occurred while retrieving file with key '{}' from bucket '{}': {}", fileStorageKey, bucketName, e.getMessage());
            throw new FileStorageServiceException("Failed to retrieve file from storage", fileStorageKey, null, PROVIDER, FileStorageOperation.READ, e);
        } 
    }



    @Override
    public Map<String, String> getFileMetadata(String fileStorageKey) 
    {
        HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
        .bucket(bucketName)
        .key(fileStorageKey)
        .build();

        try 
        {
            HeadObjectResponse response = s3Client.headObject(headObjectRequest);
            Map<String, String> photoMetadata = new HashMap<>(response.metadata()); // Metadati utente

            if (response.contentType() != null) photoMetadata.put("ContentType", (String) response.contentType());
            if (response.contentDisposition() != null) photoMetadata.put("ContentDisposition", (String) response.contentDisposition());

            log.info("Metadata for file with key '{}' retrieved successfully from Amazon S3 bucket '{}'.", fileStorageKey, bucketName);

            return photoMetadata;
        } 
        catch (NoSuchKeyException e) 
        {
            log.error("File with key '{}' not found in Amazon S3 bucket '{}', while retrieving its metadata.", fileStorageKey, bucketName);
            throw new FileStorageServiceException("File not found in storage while retrieving its metadata", fileStorageKey, null, PROVIDER, FileStorageOperation.READ, e);
        } 
        catch (SdkException e) 
        {
            log.error("Amazon S3/SDK exception occurred during metadata retrieval for file with key '{}' from bucket '{}': {}", fileStorageKey, bucketName, e.getMessage());
            throw new FileStorageServiceException("Failed to retrieve metadata of file from storage", fileStorageKey, null, PROVIDER, FileStorageOperation.READ, e);
        } 
    }


    @Override
    public String getFilePublicUrl(String fileStorageKey) 
    {
        GetUrlRequest getUrlRequest = GetUrlRequest.builder()
                                                   .bucket(bucketName)
                                                   .key(fileStorageKey)
                                                   .build();
        try 
        {
            URL url = s3Client.utilities().getUrl(getUrlRequest);
            log.info("Public URL for file with key '{}' in bucket Amazon S3 bucket '{}' retrieved successfully: {}", fileStorageKey, bucketName, url.toString());
            return url.toString();
        } 
        catch (SdkException e) 
        {
            log.error("Amazon S3/SDK exception occurred during public URL generation for file with key '{} from bucket '{}': {}", fileStorageKey, bucketName, e.getMessage());
            throw new FileStorageServiceException("Failed to generate public URL of file from storage", fileStorageKey, null, PROVIDER, FileStorageOperation.READ, e);
        } 
    }
    

    @Override
    public void deleteFile(String fileStorageKey) 
    {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
        .bucket(bucketName)
        .key(fileStorageKey)
        .build();

        try 
        {
            s3Client.deleteObject(deleteObjectRequest);
            log.info("File with key '{}' was deleted successfully from Amazon S3 bucket '{}'.", fileStorageKey, bucketName);
        } 
        catch (NoSuchKeyException e) 
        {
            log.warn("Attempted to delete non-existent file with key '{}' from Amazon S3 bucket: '{}'.", fileStorageKey, bucketName);
        } 
        catch (SdkException e) 
        {
            log.error("Amazon S3/SDK exception occurred during deletion of file with key '{}' in Amazon S3 bucket '{}': {}", fileStorageKey, bucketName, e.getMessage());
            throw new FileStorageServiceException("Failed to delete file from storage", fileStorageKey, null, PROVIDER, FileStorageOperation.DELETE, e);
        }        
    }
}