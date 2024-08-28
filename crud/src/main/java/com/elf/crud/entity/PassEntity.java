package com.elf.crud.entity;

import com.elf.crud.enumset.ApplicantType;
import com.elf.crud.enumset.ApplicationStatus;
import com.elf.crud.enumset.Role;
import com.elf.crud.enumset.Terminal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

//import java.awt.geom.Area;

@Entity
@Table(name = "pass")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassEntity {
    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "companyId" , nullable = false)
    private CompanyEntity company;

//    company name
    private String purpose;
    private String applicationNo;
    private String applicationName;
    private String icNo;
    private Integer passportNo;
    private Terminal terminal;

    @ElementCollection
    @CollectionTable(name = "pass_areas", joinColumns = @JoinColumn(name = "pass_id"))
    @Column(name = "area")
    private Set<String> areas =  new HashSet<>();

    private ApplicantType applicantType;
    private ApplicationStatus status;
    private String submittedBy;
    @CreationTimestamp
    private Date submittedDate;
    private Role role;

    @OneToOne(mappedBy = "pass", cascade = CascadeType.ALL, orphanRemoval = true)
    private PassAttachmentEntity passAttachmentFile;



}
