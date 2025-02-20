package com.cleartax.approvalSystem.controller;

import com.cleartax.approvalSystem.model.Document;
import com.cleartax.approvalSystem.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

//    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<String> uploadDocument(@RequestParam("file") MultipartFile file,
//                                                 @RequestParam("userId") Integer userId) {
//        String fileUrl = documentService.uploadDocument(file, userId);
//        return ResponseEntity.ok("Document uploaded successfully: " + fileUrl);
//    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadDocument(@RequestBody MultipartFile file) {
//        String fileUrl = documentService.uploadDocument(file);
//        return ResponseEntity.ok("Document uploaded successfully: " + fileUrl);
       return new ResponseEntity<>(documentService.uploadDocument(file), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocument(@PathVariable Integer id) {
        return ResponseEntity.ok(documentService.getDocumentById(id));
    }
}

