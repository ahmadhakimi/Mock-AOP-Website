package com.elf.crud.dto;

import com.elf.crud.enumset.Role;
import com.elf.crud.enumset.Status;
import com.elf.crud.enumset.Terminal;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String token;
//    display token expiration
    private long expiresIn;
//    show current auth staff id
    private String staffId;

    private String fullName;
    private String userName;
    private String email;
    private Terminal terminal;
    private Role role;
    private Status status;

}
