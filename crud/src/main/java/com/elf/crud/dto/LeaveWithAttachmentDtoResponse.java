package com.elf.crud.dto;

import com.elf.crud.enumset.Leave;
import com.elf.crud.enumset.Status;
import com.elf.crud.enumset.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LeaveWithAttachmentDtoResponse {
    private UUID id;
    private String staffId;
//    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate startDate;
//    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate endDate;
    private Leave leaveType;
    private Type dateType;
    private float leaveTaken;
    private Status status;
    private String remarks;
    private Date createdAt;
    private Date updatedAt;

    private String attachmentId;
    private String attachmentType;
    private String attachmentName;


}
