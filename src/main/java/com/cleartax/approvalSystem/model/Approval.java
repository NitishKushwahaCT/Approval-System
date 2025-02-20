package com.cleartax.approvalSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Approval {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer documentId;
    private boolean isApprovedByUser1;
    private boolean isApprovedByUser2;
    private boolean finalApproval;

    public Approval(Integer documentId, boolean isApprovedByUser1, boolean isApprovedByUser2, boolean finalApproval){
        this.documentId = documentId;
        this.isApprovedByUser1 = isApprovedByUser1;
        this.isApprovedByUser2 = isApprovedByUser2;
        this.finalApproval = finalApproval;
    }
}
