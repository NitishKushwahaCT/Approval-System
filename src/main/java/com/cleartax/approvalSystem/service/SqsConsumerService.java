package com.cleartax.approvalSystem.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.util.List;

@Service
public class SqsConsumerService {

    private final SqsClient sqsClient;

    @Value("${sqs.queue.url}")
    private String queueUrl;

    public SqsConsumerService(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    // Scheduled to run every 10 seconds and poll for new messages
    @Scheduled(fixedRate = 10000)
    public void pollMessages() {
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(5) // Process up to 5 messages at a time
                .waitTimeSeconds(5)      // Long polling for efficiency
                .build();

        List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).messages();

        if (messages.isEmpty()) {
            System.out.println("No new messages in SQS queue.");
            return;
        }

        for (Message message : messages) {
            processMessage(message);
            deleteMessage(message.receiptHandle());
        }
    }

    // Process the message (simulate sending an email)
    private void processMessage(Message message) {
        System.out.println("Received message: " + message.body());
        System.out.println("Document approved, email sent to user.");
    }

    // Delete the message from the queue after processing
    private void deleteMessage(String receiptHandle) {
        DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                .queueUrl(queueUrl)
                .receiptHandle(receiptHandle)
                .build();

        sqsClient.deleteMessage(deleteMessageRequest);
        System.out.println("Message deleted from SQS queue.");
    }
}

