package com.elf.crud.entity;

import com.elf.crud.enumset.CompanyStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "company")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class CompanyEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private String id;

    private String companyName;

    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate rocExpDate;

    private String telNo;

    private String faxNo;

    private String companyEmail;

    @Enumerated(EnumType.STRING)
    private CompanyStatus companyStatus;

    private String remarks;

    private String address;

    private String createdBy;

    private String updatedBy;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @JoinColumn(name = "staffId", nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private StaffEntity staff;

    @OneToOne(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private AttachmentEntity attachment;

    public void setAttachment(AttachmentEntity attachment) {
        this.attachment = attachment;
//        add this line to make sure the Company Entity storing value of company id
        if(attachment != null) {
            attachment.setCompany(this);
        }
    }
}
