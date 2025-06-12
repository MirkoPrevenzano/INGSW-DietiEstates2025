
package com.dietiEstates.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;



@Configuration
public class AmazonS3Config 
{
	@Bean
	public S3Client s3Client()
	{
		S3Client s3client = S3Client.builder()
									.region(Region.EU_SOUTH_1)
									.build();
		return s3client;
	}
}