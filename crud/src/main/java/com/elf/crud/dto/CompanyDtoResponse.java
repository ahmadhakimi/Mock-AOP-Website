package com.elf.crud.dto;

import com.elf.crud.enumset.CompanyStatus;
import com.elf.crud.enumset.Leave;
import com.elf.crud.enumset.Status;
import com.elf.crud.enumset.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDtoResponse {

    private String id;
    private String staffId;
    private String companyName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate rocExpDate;
    private String telNo;
    private String faxNo;
    private String companyEmail;
    private CompanyStatus companyStatus;
    private String remarks;
    private String address;
    private String createdBy;
    private String updatedBy;
    private Date createdAt;
    private Date updatedAt;
    private AttachmentDto attachment;

}
