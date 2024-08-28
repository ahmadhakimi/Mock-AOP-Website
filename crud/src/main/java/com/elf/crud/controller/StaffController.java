package com.elf.crud.controller;

import com.elf.crud.dto.StaffRequestDto;
import com.elf.crud.dto.StaffResponseDto;
import com.elf.crud.service.StaffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/aop")
//@CrossOrigin(origins = "http://localhost:4200") //add cors to allow connection to angular app URL
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})

public class StaffController {

    private final StaffService staffService;

    @PostMapping("/staff")
    public ResponseEntity<StaffResponseDto> createStaff (@RequestBody StaffRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(staffService.createStaff(request));
    }

    @GetMapping("/staff")
    public ResponseEntity<List<StaffResponseDto>> staffList () {
        return ResponseEntity.status(HttpStatus.OK).body(staffService.staffList());
    }

    @GetMapping("/staff/{id}")
    public ResponseEntity<StaffResponseDto> viewStaff (@PathVariable  String id) {
        return ResponseEntity.status(HttpStatus.OK).body(staffService.viewStaff(id));
    }

    @PutMapping("/staff/{id}")
    public ResponseEntity<StaffResponseDto> updateStaff (@PathVariable String id, @RequestBody StaffRequestDto request) {
        return ResponseEntity.status(HttpStatus.OK).body(staffService.updateStaff(id, request));
    }

    @DeleteMapping("/staff/{id}")
    public ResponseEntity<String> deleteStaff (@PathVariable String id){
        staffService.deleteStaff(id);
        return ResponseEntity.ok("Staff id: " + id + " successfully deleted");

    }
//    ADMIN ENDPOINT PART

//    @PostMapping("/admin")
//    public ResponseEntity<AdminDTO> createNewAdmin (AdminDTO request) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(staffService.createAdmin(request));
//    }

    @PostMapping("/admin/create-staff")
    public ResponseEntity<?> createNewStaffByAdmin (@RequestBody StaffRequestDto requestDto) {
        try {
            StaffResponseDto staff = staffService.createdStaffByAdmin(requestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Create user successful. Please check your email");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User are unauthorized");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the user");
        }
    }

//    testing admin access
    @GetMapping("/admin/admin-access")
    public ResponseEntity<String> onlyAdminAccess() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body("ADMIN ACCESS PAGE SITE ONLY");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("YOU ARE NOT AUTHORIZED TO ACCESS THIS SITE");
        }
    }




//    testing email sent
  /*  @PostMapping("/send-email")
    public ResponseEntity<String> sendTestEmail() {
        try {
            staffService.sendPasswordByEmail("testuser@gmail.com", "testPassword123");
            log.info("Successful sent message to email");
            return ResponseEntity.ok("Email sent successfully");
        } catch (Exception e) {
            log.error("Failed to send email");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email: " + e.getMessage());

        }
    }*/

}
