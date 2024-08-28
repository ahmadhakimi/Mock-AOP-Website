package com.elf.crud.repository;

import com.elf.crud.entity.PassAttachmentEntity;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassAttachmentRepository extends JpaRepository<PassAttachmentEntity, String>{
}
