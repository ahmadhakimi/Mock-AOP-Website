//package com.elf.crud.service;
//
//import com.elf.crud.dto.LeaveDtoRequest;
//import com.elf.crud.dto.LeaveDtoResponse;
//import com.elf.crud.dto.LeaveWithAttachmentDtoRequest;
//import com.elf.crud.dto.LeaveWithAttachmentDtoResponse;
//import com.elf.crud.entity.AttachmentEntity;
//import com.elf.crud.entity.LeaveEntity;
//import com.elf.crud.entity.StaffEntity;
//import com.elf.crud.repository.AttachmentRepository;
//import com.elf.crud.repository.LeaveRepository;
//import com.elf.crud.repository.StaffRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.time.Instant;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class LeaveService {
//
//    private final LeaveRepository leaveRepository;
//    private final StaffRepository staffRepository;
//    private final AttachmentRepository attachmentRepository;
//
//    //    apply leave without  attachment
////    public LeaveDtoResponse applyLeave(LeaveDtoRequest request) {
////        StaffEntity staff = staffRepository.findById(request.getStaffId())
////                .orElseThrow(() -> new NoSuchElementException("Staff not available"));
////
////        LeaveEntity leave = LeaveEntity.builder()
////                .staff(staff)
////                .startDate(request.getStartDate())
////                .endDate(request.getEndDate())
////                .leaveType(request.getLeaveType())
////                .dateType(request.getDateType())
////                .leaveTaken(request.getLeaveTaken())
//////                .status(Status.PENDING)  // Assuming all new leave requests start with a pending status
////                .remarks(request.getRemarks())
////                .build();
////
////
////        LeaveEntity save = leaveRepository.save(leave);
////        return MapToLeaveDto(save);
////
////    }
//
//    //    apply leave with attachment
//    public LeaveWithAttachmentDtoResponse applyLeaveWithAttachment(LeaveWithAttachmentDtoRequest request, MultipartFile attachmentFile) {
//        StaffEntity staff = staffRepository.findById(request.getStaffId())
//                .orElseThrow(() -> new NoSuchElementException("No staff found with id: " + request.getStaffId()));
//
//        System.out.println("Staff Found");
//
//        LeaveEntity leave = LeaveEntity.builder()
//                .staff(staff)
//                .startDate(request.getStartDate())
//                .endDate(request.getEndDate())
//                .leaveType(request.getLeaveType())
//                .dateType(request.getDateType())
//                .leaveTaken(request.getLeaveTaken())
////                .status(Status.PENDING)
//                .remarks(request.getRemarks())
//                .build();
//
//        System.out.println("Leave form write");
//
//        if (!request.getAttachmentFile().isEmpty() && request.getAttachmentFile() != null) {
//            try {
//                // create a new object of saving attachment from saveAttachment method
//                AttachmentEntity attachment = saveAttachment(request.getAttachmentFile());
//
//                // set the  empty attachment above in attachment file/ property of leave Entity
//                leave.setAttachment(attachment);
//
////                save the attachment in the leave entity
////                attachment.setLeave(leave);
//
//            } catch (IOException e) {
//                throw new RuntimeException("Failed to save  the attachment !", e);
//            }
//        }
//
//        System.out.println("Leave form with attachment successfully write");
//
//        LeaveEntity savedLeave = leaveRepository.save(leave);
//        return mapToLeaveWithAttachmentDto(savedLeave);
//
//    }
//    public List<LeaveDtoResponse> listLeave() {
//        List<LeaveEntity> list = leaveRepository.findAll();
//        return list.stream()
//                .map(this::MapToLeaveDto)
//                .collect(Collectors.toList());
//    }
//
//    public LeaveDtoResponse viewLeave(UUID id) {
//        LeaveEntity leave = leaveRepository.findById(id)
//                .orElseThrow(() -> new NoSuchElementException("No leave available: " + id));
//        return MapToLeaveDto(leave);
//    }
//
//    public LeaveDtoResponse updateLeave(UUID id, LeaveDtoRequest request) {
//
//        boolean isUpdate = false;
//
//        LeaveEntity existingLeave = leaveRepository.findById(id)
//                .orElseThrow(() -> new NoSuchElementException("No leave available: " + id));
//
//        if (request.getStartDate() != null) {
//            existingLeave.setStartDate(request.getStartDate());
//            isUpdate = true;
//        }
//
//        if (request.getEndDate() != null) {
//            existingLeave.setEndDate(request.getEndDate());
//            isUpdate = true;
//        }
//
//        if (request.getLeaveType() != null) {
//            existingLeave.setLeaveType(request.getLeaveType());
//            isUpdate = true;
//        }
//
//        if (request.getDateType() != null) {
//            existingLeave.setDateType(request.getDateType());
//            isUpdate = true;
//        }
//
//        if (request.getLeaveTaken() != existingLeave.getLeaveTaken()) {
//            existingLeave.setLeaveTaken(request.getLeaveTaken());
//            isUpdate = true;
//        }
//
//        if (request.getStatus() != null) {
//            existingLeave.setStatus(request.getStatus());
//            isUpdate = true;
//        }
//
//        if (request.getRemarks() != null) {
//            existingLeave.setRemarks(request.getRemarks());
//            isUpdate = true;
//        }
//
//        if (isUpdate) {
//            existingLeave.setUpdatedAt(Date.from(Instant.now()));
//            LeaveEntity save = leaveRepository.save(existingLeave);
//            return MapToLeaveDto(save);
//
//        } else {
//            return MapToLeaveDto(existingLeave);
//        }
//
//    }
//
//    public void deleteLeave(UUID id) {
//        leaveRepository.deleteById(id);
//    }
//
//
//    // get all list leave with attachment
//    public List<LeaveWithAttachmentDtoResponse> listAllLeaveWithAttachment() {
//        List<LeaveEntity> list = leaveRepository.findAll();
//
//        return list.stream()
//                .map(this::mapToLeaveWithAttachmentDto)
//                .collect(Collectors.toList());
//    }
//
////    private LeaveDtoResponse MapToLeaveDto(LeaveEntity leaveEntity) {
////        return LeaveDtoResponse.builder()
////                .id(leaveEntity.getId())
////                .staffId(leaveEntity.getStaff().getId())
////                .startDate(leaveEntity.getStartDate())
////                .endDate(leaveEntity.getEndDate())
////                .leaveType(leaveEntity.getLeaveType())
////                .dateType(leaveEntity.getDateType())
////                .leaveTaken(leaveEntity.getLeaveTaken())
////                .status(leaveEntity.getStatus())
////                .remarks(leaveEntity.getRemarks())
////                .createdAt(leaveEntity.getCreatedAt())
////                .updatedAt(leaveEntity.getUpdatedAt())
////                .build();
////    }
//
//    //    save attachment using Multipart file
//    private AttachmentEntity saveAttachment(MultipartFile attachmentFile) throws IOException {
//        AttachmentEntity attachment = AttachmentEntity.builder()
//                .name(attachmentFile.getOriginalFilename())
//                .data(attachmentFile.getBytes())
//                .type(attachmentFile.getContentType())
//                .build();
//
//
//
////        Save the attachment entity and return attachmentRepository.save(attachment);
//        return attachmentRepository.save(attachment);
//    }
//
//// new method for leave application with attachment
//    private LeaveWithAttachmentDtoResponse mapToLeaveWithAttachmentDto(LeaveEntity leaveEntity) {
//        AttachmentEntity attachment = leaveEntity.getAttachment();
//        return LeaveWithAttachmentDtoResponse.builder()
//                .id(leaveEntity.getId())
//                .staffId(leaveEntity.getStaff().getId())
//                .startDate(leaveEntity.getStartDate())
//                .endDate(leaveEntity.getEndDate())
//                .leaveType(leaveEntity.getLeaveType())
//                .dateType(leaveEntity.getDateType())
//                .leaveTaken(leaveEntity.getLeaveTaken())
//                .status(leaveEntity.getStatus())
//                .remarks(leaveEntity.getRemarks())
//                .createdAt(leaveEntity.getCreatedAt())
//                .updatedAt(leaveEntity.getUpdatedAt())
//                .attachmentId(attachment != null ? attachment.getId() : null)
//                .attachmentType(attachment != null ? attachment.getType() : null)
//                .attachmentName(attachment != null ? attachment.getName() : null)
//                .build();
//    }
//}
