
package com.dietiEstates.backend.service.photo.storage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
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
            log.info("File with key '{}' was uploaded successfully in Amazon S3 bucket '{}'.", photoKey, bucketName);
		} 
		catch (SdkException e)
		{
            log.error("Amazon S3/SDK exception occurred while uploading file with key '{}' in bucket '{}': {}", photoKey, bucketName, e.getMessage());
            throw new IOException("Failed to upload file in storage: " + photoKey, e);
		}        
    }


    @Override
    public byte[] downloadFile(String photoKey) throws IOException 
    {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                                                            .bucket(bucketName)
                                                            .key(photoKey)
                                                            .build();		

        try {
/*             ResponseInputStream<GetObjectResponse> responseInputStream = s3Client.getObject(getObjectRequest);
            return responseInputStream.readAllBytes(); */
            ResponseBytes<GetObjectResponse> objectBytesAndResponse = s3Client.getObjectAsBytes(getObjectRequest);
            log.info("File with key '{}' was retrieved successfully from the Amazon S3 bucket '{}'", photoKey, bucketName);
            return objectBytesAndResponse.asByteArray();
        } catch (NoSuchKeyException e) {
            log.error("File with key '{}' not found in Amazon S3 bucket '{}'.", photoKey, bucketName);
            throw new IOException("File not found in storage: " + photoKey, e);
        } catch (SdkException e) {
            log.error("Amazon S3/SDK exception occurred while retrieving file with key '{}' from bucket '{}': {}", photoKey, bucketName, e.getMessage());
            throw new IOException("Failed to retrieve file from storage: " + photoKey, e);
        } catch (Exception e) {
            log.error("Generic exception occurred while retrieving file with key '{}' from bucket '{}': {}", photoKey, bucketName, e.getMessage());
            throw new IOException("An unexpected error occurred during file download: " + photoKey, e);
        }
    }



    @Override
    public Map<String, Object> getFileMetadata(String photoKey) throws IOException 
    {
        HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
        .bucket(bucketName)
        .key(photoKey)
        .build();

        try 
        {
            HeadObjectResponse response = s3Client.headObject(headObjectRequest);
            Map<String, Object> photoMetadata = new HashMap<>(response.metadata()); // Metadati utente

            if (response.contentType() != null) photoMetadata.put("ContentType", (String) response.contentType());
            if (response.contentLength() != null) photoMetadata.put("ContentLength", (Long) response.contentLength());
            if (response.contentDisposition() != null) photoMetadata.put("ContentDisposition", (String) response.contentDisposition());

            log.info("Metadata for file with key '{}' retrieved successfully from Amazon S3 bucket '{}'.", photoKey, bucketName);
            return photoMetadata;
        } 
        catch (NoSuchKeyException e) 
        {
            log.error("File with key '{}' not found in Amazon S3 bucket '{}', while retrieving its metadata.", photoKey, bucketName);
            throw new IOException("File not found in storage while retrieving its metadata: " + photoKey, e);
        } catch (SdkException e) {
            log.error("Amazon S3/SDK exception occurred during metadata retrieval for file with key '{}' from bucket '{}': {}", photoKey, bucketName, e.getMessage());
            throw new IOException("Failed to retrieve metadata from S3: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Generic exception occurred during metadata retrieval for file with key '{}' from bucket '{}': {}", photoKey, bucketName, e.getMessage());
            throw new IOException("An unexpected error occurred during metadata retrieval: " + photoKey, e);
        }
    }


    @Override
    public String getFilePublicUrl(String photoKey) throws IOException 
    {
        GetUrlRequest getUrlRequest = GetUrlRequest.builder()
                                                   .bucket(bucketName)
                                                   .key(photoKey)
                                                   .build();
        try 
        {
            URL url = s3Client.utilities().getUrl(getUrlRequest);
            log.info("Generated public URL for file with key '{}' in bucket Amazon S3 bucket '{}': {}", photoKey, bucketName, url.toString());
            return url.toString();
        } 
        catch (SdkException e) 
        {
            log.error("Amazon S3/SDK exception during public URL generation for key '{}': {}", photoKey, e.getMessage(), e);
            throw new IOException("Failed to generate public URL for file from S3: " + e.getMessage(), e);
        } 
        catch (Exception e) 
        {
            log.error("Generic exception during public URL generation for key '{}': {}", photoKey, e.getMessage(), e);
            throw new IOException("An unexpected error occurred during public URL generation: " + e.getMessage(), e);
        }
    }
    

    @Override
    public void deleteFile(String photoKey) throws IOException {
        // TODO Auto-generated method stub
        
    }
}