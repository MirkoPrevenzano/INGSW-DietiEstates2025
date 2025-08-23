
package com.dietiestates.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;


@Configuration
public class AmazonS3Config 
{
	@Bean
	public S3Client s3Client()
	{
		return S3Client.builder()
					   .region(Region.EU_SOUTH_1)
					   .credentialsProvider(DefaultCredentialsProvider.create())
					   .build();
	}
}