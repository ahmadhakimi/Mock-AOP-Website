package com.elf.crud.repository;

import com.elf.crud.entity.StaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffRepository extends JpaRepository<StaffEntity, String> {
   Optional<StaffEntity> findByEmail(String email);
}
