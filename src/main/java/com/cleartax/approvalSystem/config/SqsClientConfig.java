package com.cleartax.approvalSystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

import java.net.URI;

@Configuration
public class SqsClientConfig {


    private final SqsConfig sqsConfig;

    @Autowired
    public SqsClientConfig(SqsConfig sqsConfig) {
        this.sqsConfig = sqsConfig;
    }

    @Bean
    public SqsClient sqsClient() {
        return SqsClient.builder()
                .region(Region.of(sqsConfig.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(
                                sqsConfig.getAccessKey(),
                                sqsConfig.getSecretKey()
                        )
                ))
                .endpointOverride(URI.create("http://localhost:4566"))
                .build();
    }

    public void sendMessage(String queueUrl, String messageBody) {
        SqsClient sqsClient = sqsClient();

        SendMessageRequest sendMessageRequest = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(messageBody)
                .delaySeconds(0)  // No delay, can be modified if needed
                .build();

        SendMessageResponse response = sqsClient.sendMessage(sendMessageRequest);

        System.out.println("Message sent with ID: " + response.messageId());
    }
}