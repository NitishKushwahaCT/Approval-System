package com.cleartax.approvalSystem.repository;

import com.cleartax.approvalSystem.model.Approval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApprovalRepository extends JpaRepository<Approval, Integer> {
    Optional<Approval> findByDocumentId(Integer documentId);
}

