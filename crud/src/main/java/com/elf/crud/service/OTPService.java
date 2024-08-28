package com.elf.crud.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OTPService {

    private final Map<String, OTPDetails> OTPMap = new HashMap<>();
    private final Map<String, String> OtpToUserEmail = new HashMap<>();
    private final JavaMailSender javaMailSender;



    public void generateAndSendOtp(String email) throws MessagingException {
//        store otp generated value in otp variable
        String otp = generateOtp();

//        set expiration of otp number for 2 minutes
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(2);
//        add into OTP map as email = key, expire time = value
        OTPMap.put(otp, new OTPDetails(email, expirationTime));
        OtpToUserEmail.put(otp, email);
//        execute function emailService
        emailService(otp, email);
    }

//    emailService working method
    private void emailService(String otp, String email)  {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try {
            helper.setTo(email); //send to email
            helper.setSubject("Your OTP Password Has Been Reset"); //title
            helper.setText("This is a new otp for your password reset: " + otp); // content
            javaMailSender.send(mimeMessage); //start send
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    //    function for otp generator
    private String generateOtp() {
        SecureRandom random = new SecureRandom();
        int lengthOTP = 6;
        StringBuilder otp =new StringBuilder();

        for (int i = 0; i < lengthOTP; i ++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }

    public boolean verifyOtpProcess(String otp) {
        OTPDetails otpDetails = OTPMap.get(otp);
        if (otpDetails == null || otpDetails.getExpirationTime().isBefore(LocalDateTime.now())){
            return false;
        }

        OTPMap.remove(otp);
        OtpToUserEmail.remove(otp);
        return true;
    }
}


