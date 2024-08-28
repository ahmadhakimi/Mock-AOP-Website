package com.elf.crud.controller;

import com.elf.crud.dto.PassRequestDto;
import com.elf.crud.dto.PassResponseDto;
import com.elf.crud.service.PassService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/aop/pass")
public class PassController {
    private final PassService passService;
    private final ObjectMapper objectMapper;

    @PostMapping()
    public ResponseEntity<PassResponseDto> createPass(
            @RequestPart("pass") String passJson,
            @RequestPart("attachment")MultipartFile attachment) throws IOException {

        PassRequestDto passRequestDto;

        passRequestDto = objectMapper.readValue(passJson, PassRequestDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(passService.createPass(passRequestDto, attachment));
    }

    @GetMapping()
    public ResponseEntity<List<PassResponseDto>> getAllPass () {
        return ResponseEntity.status(HttpStatus.OK).body(passService.getAllPass());
    }

}
