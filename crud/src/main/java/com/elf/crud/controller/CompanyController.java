package com.elf.crud.controller;

import com.elf.crud.dto.CompanyDtoRequest;
import com.elf.crud.dto.CompanyDtoResponse;
import com.elf.crud.service.CompanyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/aop/company")
@Slf4j
public class CompanyController {
    private final CompanyService companyService;
    private final ObjectMapper objectMapper;

    @PostMapping
    public ResponseEntity<CompanyDtoResponse> createCompany(
            @RequestPart("company") String companyJson,
            @RequestPart("attachment") MultipartFile attachment) throws IOException {

        log.info("Company JSON: " + companyJson);
        if (attachment != null) {
            log.info("Attachment file: " + attachment.getOriginalFilename());
        } else {
            log.warn("No attachment file provided.");
        }

        log.info("Start API call for createCompany");
        CompanyDtoRequest companyDtoRequest;

        try {
            companyDtoRequest = objectMapper.readValue(companyJson, CompanyDtoRequest.class);
            log.info("CompanyDtoRequest deserialized successfully");
        } catch (JsonProcessingException e) {
            log.error("Error deserializing company JSON", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            CompanyDtoResponse companyDtoResponse = companyService.createCompany(companyDtoRequest, attachment);
            log.info("Company created successfully");
            return new ResponseEntity<>(companyDtoResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating company", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping
    public ResponseEntity<List<CompanyDtoResponse>> companyList () {
        log.info("API called for get All Company List");
       return ResponseEntity.status(HttpStatus.OK).body(companyService.companyList());
   }

   @GetMapping("/{id}")
    public ResponseEntity<CompanyDtoResponse> viewCompany (@PathVariable String id) {
       log.info("API called to view Company by company id");
       return ResponseEntity.status(HttpStatus.OK).body(companyService.viewCompany(id));
   }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDtoResponse> updateCompany(
            @PathVariable String id,
            @RequestPart("company") String companyDtoRequestJson,
            @RequestPart(value = "attachment", required = false) MultipartFile attachmentFile) {

        log.info("----API called to update company details----");

        CompanyDtoRequest request;


        try {
            request = objectMapper.readValue(companyDtoRequestJson, CompanyDtoRequest.class);
            log.info("----Serialized CompanyDtoRequest successfully----");
        } catch (JsonProcessingException e) {
            log.error("Error serializing CompanyDtoRequest: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }

        // Set the attachment file
        request.setAttachmentFile(attachmentFile);

        // Call the service to update the company
        try {
            CompanyDtoResponse response = companyService.updateCompany(id, request);
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            log.error("Company not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error updating company: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

   @DeleteMapping("/{id}")
    public String deleteCompany (@PathVariable String id) {
        return  "The company is successfully deleted: " + id;
   }
}



 /*@PostMapping
    public ResponseEntity<CompanyDtoResponse> createCompany (
            @RequestPart("company") String companyJson,
            @RequestPart("attachment") MultipartFile attachment) throws IOException {

        System.out.println("Company: " + companyJson);
        System.out.println("Attachment file: " + attachment);


        log.info("start api call for createCompany ");
        CompanyDtoRequest companyDtoRequest;

        log.info("create object companyDtoRequest");

        try {
            companyDtoRequest = objectMapper.readValue(companyJson, CompanyDtoRequest.class);

            log.info("passing object mapper  ");

        } catch (JsonProcessingException e) {
            log.error("Error deserializing company JSON", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        CompanyDtoResponse companyDtoResponse = companyService.createCompany(companyDtoRequest, attachment);
        log.info("invoke create company service business logic");
        return new ResponseEntity<>(companyDtoResponse, HttpStatus.CREATED);
    }*/

//recent one


//    @PostMapping
//    public ResponseEntity<CompanyDtoResponse> createCompany(
//            @RequestPart("company") String companyJson,
//            @RequestPart("attachment") MultipartFile attachment) {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String staffId = (String) authentication.getPrincipal();
//
//        try {
//            // Convert the JSON string to CompanyDtoRequest
//            CompanyDtoRequest companyDto = objectMapper.readValue(companyJson, CompanyDtoRequest.class);
//            companyDto.setStaffId(staffId); // Correctly set the staffId
//
//            // You can now use companyDto and attachment to handle the request
//            // Process the companyDto and attachment here
//            companyService.createCompany(companyDto, attachment);
//
//            return ResponseEntity.ok("Company created successfully!");
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request format.");
//        }
//    }



//    @PostMapping()
//    public ResponseEntity<CompanyDtoResponse> createCompany(
//            @RequestPart("company")  CompanyDtoRequest companyDtoRequest,
//            @RequestPart("attachment") MultipartFile attachmentFile) {
//
//        CompanyDtoResponse response = companyService.createCompany(companyDtoRequest, attachmentFile);
//        return ResponseEntity.ok(response);
//    }

