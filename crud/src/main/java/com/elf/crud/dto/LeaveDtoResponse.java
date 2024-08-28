package com.elf.crud.dto;

import com.elf.crud.entity.StaffEntity;
import com.elf.crud.enumset.Leave;
import com.elf.crud.enumset.Status;
import com.elf.crud.enumset.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaveDtoResponse {

    private UUID id;
    private String staffId;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate startDate;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate endDate;
    private Leave leaveType;
    private Type dateType;
    private float leaveTaken;
    private Status status;
    private String remarks;
    private Date createdAt;
    private Date updatedAt;
}
