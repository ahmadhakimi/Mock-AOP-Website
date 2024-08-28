package com.elf.crud.service;

import com.elf.crud.dto.AttachmentDto;
import com.elf.crud.dto.CompanyDtoRequest;
import com.elf.crud.dto.CompanyDtoResponse;
import com.elf.crud.entity.AttachmentEntity;
import com.elf.crud.entity.CompanyEntity;
import com.elf.crud.entity.StaffEntity;
import com.elf.crud.repository.AttachmentRepository;
import com.elf.crud.repository.CompanyRepository;
import com.elf.crud.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final StaffRepository staffRepository;
    private final AttachmentRepository attachmentRepository;
    private final StaffService staffService;


    public CompanyDtoResponse createCompany(CompanyDtoRequest request, MultipartFile attachmentFile) throws IOException {
        // Check if the attachmentFile is null
        if (attachmentFile == null) {
            log.error("Attachment file is null");
        } else {
            log.info("Attachment file name: {}", attachmentFile.getOriginalFilename());
        }

        StaffEntity staff = staffRepository.findById(request.getStaffId())
                .orElseThrow(() -> new NoSuchElementException("Staff not available"));

        CompanyEntity company = CompanyEntity.builder()
                .staff(staff)
                .companyName(request.getCompanyName())
                .companyEmail(request.getCompanyEmail())
                .companyStatus(request.getCompanyStatus())
                .address(request.getAddress())
                .faxNo(request.getFaxNo())
                .remarks(request.getRemarks())
                .telNo(request.getTelNo())
                .rocExpDate(request.getRocExpDate())
                .createdBy(request.getCreatedBy())
                .updatedBy(request.getUpdatedBy())
                .build();

        CompanyEntity savedCompany = companyRepository.save(company);

        // Handle attachment if present
        if (attachmentFile != null && !attachmentFile.isEmpty()) {
            try {
                AttachmentEntity newAttachment = saveAttachment(attachmentFile, company);
                company.setAttachment(newAttachment);
            } catch (IOException e) {
                log.error("Failed to save the attachment", e);
                throw new RuntimeException("Failed to save the attachment", e);
            }
        }
        return mapToDtoCompany(savedCompany);
    }

    public List<CompanyDtoResponse> companyList() {
        List<CompanyEntity> companyEntityList = companyRepository.findAll();
        return companyEntityList.stream()
                .map(this::mapToDtoCompany)
                .collect(Collectors.toList());
    }

    public CompanyDtoResponse viewCompany(String id) {
        CompanyEntity company = companyRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No company with id " + id));
        return mapToDtoCompany(company);
    }

    public CompanyDtoResponse updateCompany (String id, CompanyDtoRequest request) {
        CompanyEntity existingCompany = companyRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No company with id " + id));
        boolean isUpdate = false;

        if (request.getCompanyName() != null) {
            existingCompany.setCompanyName(request.getCompanyName());
            isUpdate = true;
        }

        if (request.getRocExpDate() != null) {
            existingCompany.setRocExpDate(request.getRocExpDate());
            isUpdate = true;
        }

        if (request.getTelNo() != null) {
            existingCompany.setTelNo(request.getTelNo());
            isUpdate = true;
        }

        if (request.getFaxNo() != null) {
            existingCompany.setFaxNo(request.getFaxNo());
            isUpdate = true;
        }

        if (request.getCompanyEmail() != null) {
            existingCompany.setCompanyEmail(request.getCompanyEmail());
            isUpdate = true;
        }

        if (request.getCompanyStatus() != null) {
            existingCompany.setCompanyStatus(request.getCompanyStatus());
            isUpdate = true;
        }

        if (request.getRemarks() != null) {
            existingCompany.setRemarks(request.getRemarks());
            isUpdate = true;
        }

        if (request.getAddress() != null) {
            existingCompany.setAddress(request.getAddress());
            isUpdate = true;
        }

        if (request.getCreatedBy() != null) {
            existingCompany.setCreatedBy(request.getCreatedBy());
            isUpdate = true;
        }

        if (request.getUpdatedBy() != null) {
            existingCompany.setUpdatedBy(request.getUpdatedBy());
            isUpdate = true;
        }

        if (request.getAttachmentFile() != null && !request.getAttachmentFile().isEmpty()) {

            try {
//                get attachment request from request object, and passed the getAttachmentFile to the saveAttachment method
                AttachmentEntity newAttachment = saveAttachment(request.getAttachmentFile(), existingCompany);
//                remove old attachment file in the existing company if it exists

                if (existingCompany.getAttachment() != null) {
                    attachmentRepository.delete(existingCompany.getAttachment());
                }

                existingCompany.setAttachment(newAttachment);
                isUpdate = true;

            } catch (IOException e) {
                throw new RuntimeException("Failed to save an attachment! " , e);
            }

        }

        if (isUpdate) {
            existingCompany.setUpdatedAt(Date.from(Instant.now()));
            CompanyEntity updatedCompany = companyRepository.save(existingCompany);
            return mapToDtoCompany(updatedCompany);
        } else {
            return mapToDtoCompany(existingCompany);
        }

    }

    public void deleteCompany (String companyId) {
//        check if company with id input is exist or not
        CompanyEntity existingCompany = companyRepository.findById(companyId).orElseThrow(() -> new NoSuchElementException("No company with id " + companyId));

//        get attachment from the company
        AttachmentEntity attachment = existingCompany.getAttachment();

//        if attachment in company is not empty
        if (attachment != null) {
//            delete the attachment
            attachmentRepository.delete(attachment);
        }

//        delete company

        companyRepository.delete(existingCompany);
    }

    private AttachmentEntity saveAttachment(MultipartFile attachmentFile, CompanyEntity company) throws IOException {
        AttachmentEntity attachment = AttachmentEntity.builder()
                .name(attachmentFile.getOriginalFilename())
                .data(attachmentFile.getBytes())
                .type(attachmentFile.getContentType())
                .company(company)
                .build();
        return attachmentRepository.save(attachment);
    }

    private CompanyDtoResponse mapToDtoCompany(CompanyEntity saveCompany) {
        AttachmentEntity attachmentEntity = saveCompany.getAttachment();

//        add AttachmentDto object and set to null first
        AttachmentDto attachmentDto = null;

//        check if the attachment exists or not
        if (attachmentEntity != null) {
//            use builder to build a new attachment object
            attachmentDto = AttachmentDto.builder()
                    .id(attachmentEntity.getId())
                    .name(attachmentEntity.getName())
                    .type(attachmentEntity.getType())
                    .build();
        }
        return CompanyDtoResponse.builder()
                .id(saveCompany.getId())
                .staffId(saveCompany.getStaff().getId())
                .companyName(saveCompany.getCompanyName())
                .rocExpDate(saveCompany.getRocExpDate())
                .telNo(saveCompany.getTelNo())
                .faxNo(saveCompany.getFaxNo())
                .remarks(saveCompany.getRemarks())
                .companyStatus(saveCompany.getCompanyStatus())
                .companyEmail(saveCompany.getCompanyEmail())
                .remarks(saveCompany.getRemarks())
                .address(saveCompany.getAddress())
                .createdBy(saveCompany.getCreatedBy())
                .updatedBy(saveCompany.getUpdatedBy())
                .createdAt(saveCompany.getCreatedAt())
                .updatedAt(saveCompany.getUpdatedAt())
                /*.attachmentId(attachment != null ? saveCompany.getAttachment().getId() : null )
                .attachmentName(attachment != null ? saveCompany.getAttachment().getName(): null)
                .attachmentType(attachment != null ? saveCompany.getAttachment().getType(): null)*/
//                add attachment and map into the company dto response
                .attachment(attachmentDto)
                .build();

//        method to map the company as output return
    }



}

