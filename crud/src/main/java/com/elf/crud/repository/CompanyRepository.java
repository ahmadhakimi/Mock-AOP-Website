package com.elf.crud.repository;

import com.elf.crud.entity.CompanyEntity;
import org.hibernate.cache.spi.entry.CacheEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository <CompanyEntity, String> {
}
