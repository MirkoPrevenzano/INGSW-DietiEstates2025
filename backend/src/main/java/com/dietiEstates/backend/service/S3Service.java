
package com.dietiEstates.backend.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;


@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service 
{
    private final S3Client s3Client;

	@Value("${aws.bucket.name}")
	private String bucketName;



	public void putObject(String photoKey, byte[] file) throws SdkException
	{
		PutObjectRequest putObjectRequest = PutObjectRequest.builder()
															.bucket(bucketName)
															.key(photoKey)
															.build();															
		try 
		{
			s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file));
			log.info("Photo was saved successfully in AmazonS3");
		} 
		catch (SdkException e)
		{
            log.error("Amazon S3/SDK exception has occurred while putting photo in the bucket!");
            throw e;	
		}
	}	

    
	public byte[] getObject(String photoKey) throws NoSuchKeyException, SdkException, IOException
	{
		GetObjectRequest getObjectRequest = GetObjectRequest.builder()
															.bucket(bucketName)
															.key(photoKey)
															.build();								
		try 
		{
			ResponseInputStream<GetObjectResponse> responseInputStream = s3Client.getObject(getObjectRequest);	
			log.info("Photo was retrieved successfully from AmazonS3");
			return responseInputStream.readAllBytes();		
		} 
		catch (NoSuchKeyException e) 
		{
			log.error("Photo key specified in the request was not found!");
            throw e;
		}
		catch (SdkException e)
		{
            log.error("Amazon S3/SDK exception has occurred while getting photo in the bucket!");
            throw e;	
		}
		catch (IOException e) 
		{
			log.error("I/O exception has occurred while getting photo!");
			throw new IOException("I/O exception has occurred while getting photo!");
		}
	}
}
