package com.elf.crud.repository;

import com.elf.crud.entity.AttachmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<AttachmentEntity, String> {
}
