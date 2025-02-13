package com.cleartax.approvalSystem.service;

import com.cleartax.approvalSystem.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cleartax.approvalSystem.model.Document;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentService {

    @Autowired
    private final AmazonS3 amazonS3;

    private final DocumentRepository documentRepository;


    @Value("${aws.s3.bucket}")
    private String bucketName;

//    public String uploadDocument(MultipartFile file, Integer userId) {
//        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
//
//        try {
//            ObjectMetadata metadata = new ObjectMetadata();
//            metadata.setContentLength(file.getSize());
//            amazonS3.putObject(bucketName, fileName, file.getInputStream(), metadata);
//
//            String fileUrl = amazonS3.getUrl(bucketName, fileName).toString();
//
//            // Save document details in DB
//            Document document = new Document();
//            document.setDocumentUrl(fileUrl);
//            document.setUserId(userId);
//            documentRepository.save(document);
//
//            return fileUrl;
//        } catch (IOException e) {
//            throw new RuntimeException("Error uploading file to S3", e);
//        }
//    }

    public String uploadDocument(MultipartFile file){
        File fileObj = convertMultiPartToFile(file);
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        amazonS3.putObject(bucketName, fileName, fileObj);
        fileObj.delete();
        return "file uploaded successfully" + " : " + fileName;
    }

    private File convertMultiPartToFile(MultipartFile file) {
        File convFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            // throw new RuntimeException("Error converting file to S3", e);
            log.error("Error converting MultipartFile to file", e);
        }
        return convFile;
    }
    public Document getDocumentById(Integer id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found with ID: " + id));
    }
}

