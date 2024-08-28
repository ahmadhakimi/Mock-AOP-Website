package com.elf.crud.service;

import jakarta.annotation.security.DenyAll;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OTPDetails {
    private String userEmail;
    private LocalDateTime expirationTime;
}
