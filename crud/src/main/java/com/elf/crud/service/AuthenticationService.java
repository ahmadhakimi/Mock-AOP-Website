package com.elf.crud.service;

import com.elf.crud.dto.*;
import com.elf.crud.entity.StaffEntity;
import com.elf.crud.enumset.Role;
import com.elf.crud.enumset.Status;
import com.elf.crud.enumset.TokenType;
import com.elf.crud.exception.InvalidOtpException;
import com.elf.crud.exception.PasswordMismatchException;
import com.elf.crud.repository.StaffRepository;
import com.elf.crud.token.Token;
import com.elf.crud.token.TokenRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor

public class AuthenticationService {

    private final StaffRepository staffRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final OTPService otpService;

    public AuthenticationResponse register(RegisterRequest request) {
        StaffEntity staff = StaffEntity.builder()
                .userName(request.getUserName())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .terminal(request.getTerminal())
                .role(request.getRole())
                .status(Status.ACTIVE)
                .createdBy("admin")
                .updatedBy("admin")
                .deleted(false)
                .build();

        StaffEntity saveStaff = staffRepository.save(staff);
        String jwtToken = jwtService.generateToken(saveStaff);


        saveStaffToken(saveStaff, jwtToken);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest login) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()));
        StaffEntity staff = staffRepository.findByEmail(login.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("No username found"));

        //        invalid all token from a user
        revokeAllUserToken(staff);

        String jwtToken = jwtService.generateToken(staff);

//        stored value of extractExpiration into expirationDate
        Date expirationDate = jwtService.extractExpiration(jwtToken);

        //        stored time expiration in milliseconds in expiresIn object
        long expiresIn = expirationDate.getTime() - System.currentTimeMillis();

        saveStaffToken(staff, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .expiresIn(expiresIn)
                .staffId(staff.getId())
                .fullName(staff.getFullName())
                .userName(staff.getUsername())
                .email(staff.getEmail())
                .role(staff.getRole())
                .status(staff.getStatus())
                .terminal(staff.getTerminal())
                .build();
    }


    public AdminDTO createAdmin(AdminDTO request) {
        StaffEntity admin = mapToAdminEntity(request);
        StaffEntity saveAdmin = staffRepository.save(admin);
        String jwtToken =jwtService.generateToken(saveAdmin);
        return mapToAdminDTO(saveAdmin, jwtToken);

    }

    private void saveStaffToken(StaffEntity saveStaff, String jwtToken) {
        Token newToken = Token.builder()
                .staff(saveStaff)
                .email(saveStaff.getEmail())
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();

        tokenRepository.save(newToken);
    }

    private void revokeAllUserToken(StaffEntity staff) {

        List<Token> allValidTokenByUser = tokenRepository.findAllValidTokenByUser(staff.getId());

        if(allValidTokenByUser.isEmpty()) {
            return;
        }

        for (Token tokens : allValidTokenByUser) {
            tokens.setExpired(true);
            tokens.setRevoked(true);
        }

        tokenRepository.saveAll(allValidTokenByUser);

    }

    private AdminDTO mapToAdminDTO(StaffEntity staff, String token) {
        return AdminDTO.builder()
                .id(staff.getId())
                .fullName(staff.getFullName())
                .userName(staff.getUsername())
                .email(staff.getEmail())
                .password(staff.getPassword())
                .terminal(staff.getTerminal())
                .role(staff.getRole())
                .status(staff.getStatus())
                .createdBy(staff.getCreatedBy())
                .updatedBy(staff.getUpdatedBy())
                .deleted(staff.isDeleted())
                .createdAt(staff.getCreatedAt())
                .updatedAt(staff.getUpdatedAt())
                .token(token)
                .build();
    }

    private StaffEntity mapToAdminEntity(AdminDTO request) {
        return StaffEntity.builder()
                .email(request.getEmail())
                .fullName(request.getFullName())
                .userName(request.getUserName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .terminal(request.getTerminal())
                .role(Role.ADMIN)
                .status(Status.ACTIVE)
                .createdBy(request.getCreatedBy())
                .updatedBy(request.getUpdatedBy())
                .deleted(false)
                .build();

    }

    public void forgotPassword(String email) {
        try {
            otpService.generateAndSendOtp(email);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void verifyOtp(String otp) {
        boolean isOTPVerified = otpService.verifyOtpProcess(otp);
        if(!isOTPVerified){
            throw new InvalidOtpException("Invalid OTP");
        }
    }

    public void resetPassword(ResetPasswordRequest request) {
        String email = request.getEmail();
        String newPassword = request.getNewPassword();
        String confirmPassword = request.getConfirmPassword();

        if(!newPassword.equals(confirmPassword)){
            throw new PasswordMismatchException("Your password does not match!");
        } else {
            StaffEntity staff = staffRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with " + email + " not found!"));
            staff.setPassword(passwordEncoder.encode(request.getConfirmPassword()));
            staffRepository.save(staff);
        }
    }
}
