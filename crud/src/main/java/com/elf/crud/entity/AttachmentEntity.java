package com.elf.crud.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity

@Table (name = "attachment")
public class AttachmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;

    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] data;
    private String type;

    @CreationTimestamp
    private LocalDate createdAt;

    @UpdateTimestamp
    private LocalDate updatedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyId", nullable = false)
    private CompanyEntity company;


}
