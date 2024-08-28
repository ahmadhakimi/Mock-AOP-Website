package com.elf.crud.dto;

import com.elf.crud.entity.AttachmentEntity;
import com.elf.crud.enumset.CompanyStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDtoRequest {
    private String staffId;
    private String companyName;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate rocExpDate;
    private String telNo;
    private String faxNo;
    private String companyEmail;
    private CompanyStatus companyStatus;
    private String remarks;
    private String address;
    private String createdBy;
    private String updatedBy;

//    @JsonProperty("attachment")
    private MultipartFile attachmentFile;



}
