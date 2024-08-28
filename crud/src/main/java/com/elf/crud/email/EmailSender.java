package com.elf.crud.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
@Component
@Slf4j
public class EmailSender {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String subject, String body) {
       /* MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            javaMailSender.send(message);
            log.info("Test email sent to {}", to);
        } catch (MessagingException e) {
            log.error("Failed to send email to {}", to, e);
            throw new RuntimeException("Failed to send test email", e);
        }
        javaMailSender.send(message);*/

        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("hakimihakimi611@gmail.com");
            simpleMailMessage.setTo(to);
            simpleMailMessage.setText(body);
            simpleMailMessage.setSubject(subject);

            javaMailSender.send(simpleMailMessage);

            log.info("The email has been sent to {}" , to);
        } catch (Exception e) {
            log.error("Failed send email to {}", to);
            throw new RuntimeException("Failed to send email", e);
        }

    }

}
