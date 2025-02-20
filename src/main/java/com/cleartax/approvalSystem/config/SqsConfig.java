package com.cleartax.approvalSystem.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.net.URI;

@Configuration
@Data
public class SqsConfig {
    @Value("${sqs.queue.name}")
    private String queueName;

    @Value("${sqs.queue.url}")
    private String queueUrl;

    @Value("${sqs.queue.region}")
    private String region;

    @Value("${sqs.queue.access-key}")
    private String accessKey;

    @Value("${sqs.queue.secret-key}")
    private String secretKey;

    @Value("${sqs.queue.session-token}")
    private String sessionToken;

}
