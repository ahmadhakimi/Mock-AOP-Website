package com.elf.crud.service;


    import com.elf.crud.dto.StaffRequestDto;
    import com.elf.crud.dto.StaffResponseDto;
    import com.elf.crud.entity.StaffEntity;
    import com.elf.crud.repository.StaffRepository;
    import jakarta.mail.MessagingException;
    import jakarta.mail.internet.MimeMessage;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.mail.javamail.JavaMailSender;
    import org.springframework.mail.javamail.MimeMessageHelper;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Service;

    import java.security.SecureRandom;
    import java.time.Instant;
    import java.util.Date;
    import java.util.List;
    import java.util.NoSuchElementException;
    import java.util.stream.Collectors;

    @Service
    @RequiredArgsConstructor
    @Slf4j
    public class StaffService {

        private final StaffRepository staffRepository;
        private final JavaMailSender javaMailSender;
        private final PasswordEncoder passwordEncoder;
        private final AuthenticationService authenticationService;
        private final OTPService otpService;

    public StaffResponseDto createStaff (StaffRequestDto requestDto) {
        StaffEntity staff = mapToStaffEntity(requestDto);
        StaffEntity save = staffRepository.save(staff);
        return mapToStaffDto(save);
    }

    public List<StaffResponseDto> staffList() {
        List<StaffEntity> list = staffRepository.findAll();

        return list.stream()
                .map(this::mapToStaffDto)
                .collect(Collectors.toList());
    }

    public StaffResponseDto viewStaff(String id) {
        StaffEntity staffById = staffRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No staff id: " + id));
        return mapToStaffDto(staffById);
    }

    public StaffResponseDto updateStaff(String id, StaffRequestDto request) {
        StaffEntity existingStaff = staffRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No staff with id: " + id));

        boolean isUpdate = false;

        if (request.getFullName() != null){
            existingStaff.setFullName(request.getFullName());
            isUpdate = true;
        }

        if (request.getEmail() != null){
            existingStaff.setEmail(request.getEmail());
            isUpdate = true;
        }

        if (request.getUserName() != null) {
            existingStaff.setUserName(request.getUserName());
        }

        if (request.getPassword() != null){
            existingStaff.setPassword(request.getPassword());
            isUpdate = true;
        }

        if (request.getRole() != null){
            existingStaff.setRole(request.getRole());
            isUpdate = true;
        }

        if (request.getCreatedBy() != null){
            existingStaff.setCreatedBy(request.getCreatedBy());
            isUpdate = true;
        }

        if (request.getUpdatedBy() != null){
            existingStaff.setUpdatedBy(request.getUpdatedBy());
            isUpdate = true;
        }

        if (request.getTerminal() != null) {
            existingStaff.setTerminal(request.getTerminal());
            isUpdate = true;
        }

        if (request.getStatus() != null) {
            existingStaff.setStatus(request.getStatus());
            isUpdate = true;
        }


        if (request.isDeleted() != existingStaff.isDeleted()) {
            existingStaff.setDeleted(request.isDeleted());
            isUpdate = true;
        }

        if (isUpdate){
            existingStaff.setUpdatedAt(Date.from(Instant.now()));
            StaffEntity save = staffRepository.save(existingStaff);
            return mapToStaffDto(save);
        } else {
            return mapToStaffDto(existingStaff);
        }
    }

    public void deleteStaff(String id) {
        StaffEntity staff = staffRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No staff with id: " + id));

        staff.setDeleted(true);
        staffRepository.save(staff);
    }

//    ADMIN PART

/*
    public AdminDTO createAdmin(AdminDTO request) {
        StaffEntity adminStaff = mapToAdminEntity(request);
        StaffEntity admin = staffRepository.save(adminStaff);
        return mapToAdminDTO(admin);
    }
*/

    public StaffResponseDto createdStaffByAdmin(StaffRequestDto requestDto) {
        log.info("creating staff with info {}", requestDto);
        String generatedPassword = generateRandomPassword();
        String encodedPassword = passwordEncoder.encode(generatedPassword);
        StaffEntity staff = mapToStaffEntityCreatedByAdmin(requestDto);
        staff.setPassword(encodedPassword);

        StaffEntity saveStaff = staffRepository.save(staff);
        sendPasswordByEmail(saveStaff.getEmail(), generatedPassword);
        return mapToStaffDto(saveStaff);
    }
//
////    get current auth staff id
//        @Transactional(readOnly = true)
//        public StaffEntity getCurrentUser() {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            if (authentication != null && authentication.getPrincipal() instanceof StaffEntity) {
//                return (StaffEntity) authentication.getPrincipal();
//            }
//            throw new RuntimeException("No authenticated user found");
//        }

    private void sendPasswordByEmail(String email, String password) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(email);
            helper.setSubject("Welcome to AVSEC ONLINE SYSTEM registration.");
            helper.setText("Your account email: " + email + " has been created. Your password is: " + password + ". Please login using your email and password given");
            javaMailSender.send(message);
            log.info("Password sent to {}", email);
        } catch (MessagingException e) {
            log.error("Failed to send email to {}", email, e);
            throw new RuntimeException("Failed to send email", e);
        }

    }


    private String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }

    private StaffEntity mapToStaffEntity(StaffRequestDto request) {
        return StaffEntity.builder()
                .fullName(request.getFullName())
                .userName(request.getUserName())
                .email(request.getEmail())
                .password(request.getPassword())
                .terminal(request.getTerminal())
                .role(request.getRole())
                .status(request.getStatus())
                .createdBy(request.getCreatedBy())
                .updatedBy(request.getUpdatedBy())
                .deleted(request.isDeleted())
                .build();
    }

    private StaffResponseDto mapToStaffDto (StaffEntity staff) {
        return StaffResponseDto.builder()
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
                .build();
        }

    private StaffEntity mapToStaffEntityCreatedByAdmin(StaffRequestDto request) {
        return StaffEntity.builder()
                .fullName(request.getFullName())
                .userName(request.getUserName())
                .email(request.getEmail())
                .terminal(request.getTerminal())
                .role(request.getRole())
                .status(request.getStatus())
                .createdBy("Admin")
                .updatedBy("Admin")
                .deleted(request.isDeleted())
                .build();
        }


    }
