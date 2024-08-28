package com.elf.crud.dto;

import com.elf.crud.enumset.Role;
import com.elf.crud.enumset.Status;
import com.elf.crud.enumset.Terminal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String fullName;
    private String userName;
    private String email;
    private String password;
    private Terminal terminal;
    private Role role;
    private Status status;
    private String createdBy;
    private String updatedBy;
    private boolean deleted;
}
