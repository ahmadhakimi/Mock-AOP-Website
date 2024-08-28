package com.elf.crud.dto;

import com.elf.crud.enumset.ApplicantType;
import com.elf.crud.enumset.ApplicationStatus;
import com.elf.crud.enumset.Role;
import com.elf.crud.enumset.Terminal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PassResponseDto {

    private Integer id;

    private String companyId;
    private String companyName;

    private String purpose;
    private String applicationNo;
    private String applicationName;
    private String icNo;
    private Integer passportNo;
    private Terminal terminal;

    private Set<String> areas; // Ensure this matches the backend's expectation
    private ApplicantType applicantType;
    private ApplicationStatus status;
    private String submittedBy;

    private Date submittedDate;
    private Role role;

    private String attachmentId;
    private String attachmentType;
    private String attachmentName;

}
