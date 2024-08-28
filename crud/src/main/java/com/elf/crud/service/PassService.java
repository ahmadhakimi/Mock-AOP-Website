package com.elf.crud.service;

import com.elf.crud.dto.PassRequestDto;
import com.elf.crud.dto.PassResponseDto;
import com.elf.crud.entity.CompanyEntity;
import com.elf.crud.entity.PassAttachmentEntity;
import com.elf.crud.entity.PassEntity;
import com.elf.crud.repository.CompanyRepository;
import com.elf.crud.repository.PassAttachmentRepository;
import com.elf.crud.repository.PassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PassService {

    private final PassRepository passRepository;
    private final CompanyRepository companyRepository;
    private final PassAttachmentRepository passAttachmentRepository;


    public PassResponseDto createPass(PassRequestDto requestDto, MultipartFile attachment) throws IOException {

        // Fetch the company entity
        CompanyEntity company = companyRepository.findById(requestDto.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company Not Found!"));

        // Create the PassEntity object
        PassEntity pass = PassEntity.builder()
                .company(company)
                .purpose(requestDto.getPurpose())
                .applicationNo(requestDto.getApplicationNo())
                .icNo(requestDto.getIcNo())
                .passportNo(requestDto.getPassportNo())
                .terminal(requestDto.getTerminal())
                .areas(requestDto.getAreas())
                .applicantType(requestDto.getApplicantType())
                .status(requestDto.getStatus())
                .submittedBy(requestDto.getSubmittedBy())
                .role(requestDto.getRole())
                .build();

        // Save the PassEntity first
        PassEntity savedPass = passRepository.save(pass);

        // Check and handle attachment
        if (attachment != null && !attachment.isEmpty()) {
            PassAttachmentEntity attachmentFile = PassAttachmentEntity.builder()
                    .name(attachment.getOriginalFilename())
                    .data(attachment.getBytes())
                    .type(attachment.getContentType())
                    .pass(savedPass) // Set the reference to the saved PassEntity
                    .build();
            PassAttachmentEntity savedAttachment = passAttachmentRepository.save(attachmentFile);

            // Update PassEntity with the saved attachment
            savedPass.setPassAttachmentFile(savedAttachment);
            passRepository.save(savedPass); // Save the updated PassEntity
        } else {
            throw new FileNotFoundException("Failed to find the attachment!");
        }

        // Return the response DTO
        return mapToPassDto(savedPass);
    }

    public List<PassResponseDto> getAllPass () {

        List<PassEntity> allPasses = passRepository.findAll();

        return allPasses.stream()
                .map(this::mapToPassDto)
                .collect(Collectors.toList());

    }

    private PassResponseDto mapToPassDto(PassEntity entity){
       return PassResponseDto.builder()
                .id(entity.getId())
                .companyId(entity.getCompany().getId())
                .companyName(entity.getCompany().getCompanyName())
                .purpose(entity.getPurpose())
                .applicationNo(entity.getApplicationNo())
                .applicationName(entity.getApplicationName())
                .icNo(entity.getIcNo())
                .passportNo(entity.getPassportNo())
                .terminal(entity.getTerminal())
                .areas(entity.getAreas())
                .applicantType(entity.getApplicantType())
                .status(entity.getStatus())
                .submittedBy(entity.getSubmittedBy())
                .submittedDate(entity.getSubmittedDate())
                .role(entity.getRole())
                .attachmentId(entity.getPassAttachmentFile().getId())
                .attachmentName(entity.getPassAttachmentFile().getName())
                .attachmentType(entity.getPassAttachmentFile().getType())
                .build();
    }
}
