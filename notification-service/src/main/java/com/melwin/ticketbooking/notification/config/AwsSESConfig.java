package com.melwin.ticketbooking.notification.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sesv2.SesV2Client;

@Configuration
public class AwsSESConfig {

	@Bean
	SesV2Client sesClient(StaticCredentialsProvider staticCredentialsProvider) {
		return SesV2Client.builder().region(Region.US_EAST_1).credentialsProvider(staticCredentialsProvider).build();
	}

}