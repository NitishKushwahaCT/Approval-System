package com.cleartax.approvalSystem.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.cleartax.approvalSystem.config.SqsClientConfig;
import com.cleartax.approvalSystem.model.Approval;
import com.cleartax.approvalSystem.repository.ApprovalRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class ApprovalService {


    private final ApprovalRepository approvalRepository;

    @Autowired
    private final SqsClientConfig sqs;

    @Value("${sqs.queue.url}")
    private String queueUrl;

    public String approveDocument(Integer documentId, boolean isUser1, boolean isUser2, boolean approvalStatus) {
        Approval approval = approvalRepository.findByDocumentId(documentId)
                .orElse(new Approval(documentId, false, false, false));

        if (isUser1) {
            approval.setApprovedByUser1(approvalStatus);
        } else if(isUser2){
            approval.setApprovedByUser2(approvalStatus);
        }

        // If both users approved, mark final approval and send message to SQS
        if (approval.isApprovedByUser1() && approval.isApprovedByUser2()) {
            approval.setFinalApproval(true);
            sqs.sendMessage(queueUrl, String.valueOf(documentId));
        }

        approvalRepository.save(approval);
        return "Approval updated";
    }
}

