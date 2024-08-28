package com.elf.crud.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResetPasswordRequest {
    private String email;
    private String newPassword;
    private String confirmPassword;
}
