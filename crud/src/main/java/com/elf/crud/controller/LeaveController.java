//package com.elf.crud.controller;
//
//import com.elf.crud.dto.LeaveDtoRequest;
//import com.elf.crud.dto.LeaveDtoResponse;
//import com.elf.crud.dto.LeaveWithAttachmentDtoRequest;
//import com.elf.crud.dto.LeaveWithAttachmentDtoResponse;
//import com.elf.crud.repository.LeaveRepository;
//import com.elf.crud.service.LeaveService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/lrs/leave")
//
//public class LeaveController {
//
//    private final LeaveRepository leaveRepository;
//    private final LeaveService leaveService;
//
//    @PostMapping
//    public ResponseEntity<LeaveDtoResponse> applyLeave (@RequestBody LeaveDtoRequest request) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(leaveService.applyLeave(request));
//    }
//
//    @GetMapping
//    public ResponseEntity<List<LeaveDtoResponse>> allLeaveList () {
//        return ResponseEntity.status(HttpStatus.OK).body(leaveService.listLeave());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<LeaveDtoResponse> viewLeave (@PathVariable UUID id) {
//        return ResponseEntity.status(HttpStatus.OK).body(leaveService.viewLeave(id));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<LeaveDtoResponse> updateLeave (@PathVariable UUID id, @RequestBody LeaveDtoRequest request) {
//        return ResponseEntity.status(HttpStatus.OK).body(leaveService.updateLeave(id, request));
//    }
//
//    @DeleteMapping("/{id}")
//    public String deleteLeave (@PathVariable UUID id) {
//        leaveService.deleteLeave(id);
//        return "Leave application: " + id + " deleted";
//    }
//
////    create leave with attachment
////    @PostMapping("/attachment")
////    public ResponseEntity<LeaveWithAttachmentDtoResponse> applyLeaveWithAttachment (
////            @RequestPart ("leave") LeaveWithAttachmentDtoRequest request,
////            @RequestPart ("attachment") MultipartFile attachment) {
////
////        return ResponseEntity.status(HttpStatus.CREATED).body(leaveService.applyLeaveWithAttachment(request, attachment));
////
////    }
//
//
//    @PostMapping("/attachment")
//    public ResponseEntity<LeaveWithAttachmentDtoResponse> applyLeaveWithAttachment(
//            @RequestPart("leave") LeaveWithAttachmentDtoRequest request,
//            @RequestPart("attachment") MultipartFile attachment) {
//
//        System.out.println("Read the request" + request);
//
//        System.out.println("Read attachment" + attachment);
//
//        LeaveWithAttachmentDtoResponse response = leaveService.applyLeaveWithAttachment(request, attachment);
//
//        System.out.println("Successfully passed the params");
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//    }
//
//
//    @GetMapping("/attachment")
//    public ResponseEntity<List<LeaveWithAttachmentDtoResponse>> listAllLeaveWithAttachment() {
//        return ResponseEntity.status(HttpStatus.OK).body(leaveService.listAllLeaveWithAttachment());
//    }
//
//}
