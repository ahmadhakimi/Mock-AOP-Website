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

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LeaveWithAttachmentDtoRequest {

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

    private MultipartFile attachmentFile;
}
