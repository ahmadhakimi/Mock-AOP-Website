package com.elf.crud.controller;

import com.elf.crud.dto.*;
import com.elf.crud.service.AuthenticationService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterRequest request) {
        authenticationService.register(request);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User Registered Successfully!");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest login) {
        return ResponseEntity.ok(authenticationService.authenticate(login));
    }

    @PostMapping("/admin")
    public ResponseEntity<AdminDTO> createNewAdmin (@RequestBody AdminDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.createAdmin(request));
    }

    @GetMapping("/test")
    public ResponseEntity<String> testSecuredEndpoint () {
        return ResponseEntity.ok("Access Secured Endpoint Successfully!!!!");
    }

    //    reset password endpoint
    @PostMapping("/generate")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<RenewPasswordResponseDto> forgotPassword(@RequestParam("email") String email) {
        authenticationService.forgotPassword(email);
        return ResponseEntity.ok(new RenewPasswordResponseDto("The otp successfully send to your email for password reset"));
    }

//    regenerate new otp
    @PostMapping("/resend")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<RenewPasswordResponseDto> resendOtp (@RequestParam("email") String email) {
        authenticationService.forgotPassword(email);
        return ResponseEntity.ok(new RenewPasswordResponseDto("A new OTP has been sent to your email."));
    }

//    check otp matching url
    @PostMapping("/verify")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<RenewPasswordResponseDto> verifyOtp (@RequestParam("otp") String otp) {
        authenticationService.verifyOtp(otp);
        return ResponseEntity.ok(new RenewPasswordResponseDto("Password Reset Successful"));
    }


//    reset password endpoint

    @PostMapping("/reset")
    public ResponseEntity<RenewPasswordResponseDto> resetPassword(@RequestBody ResetPasswordRequest request){
        authenticationService.resetPassword(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new RenewPasswordResponseDto("Password successfully reset. Please login with new password"));
    }

}
