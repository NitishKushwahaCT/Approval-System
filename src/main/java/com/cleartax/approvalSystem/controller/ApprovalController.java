package com.cleartax.approvalSystem.controller;

import com.cleartax.approvalSystem.service.ApprovalService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/approvals")
@RequiredArgsConstructor
public class ApprovalController {

    @Autowired
    private final ApprovalService approvalService;

    @PostMapping("/{documentId}")
    public ResponseEntity<String> approveDocument(@PathVariable Integer documentId,
                                                  @RequestParam("isUser1") boolean isUser1,
                                                  @RequestParam("isUser2") boolean isUser2,
                                                  @RequestParam("approvalStatus") boolean approvalStatus) {
        String response = approvalService.approveDocument(documentId, isUser1, isUser2, approvalStatus);
        return ResponseEntity.ok(response);
    }
}

