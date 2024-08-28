//package com.elf.crud.entity;
//
//import com.elf.crud.enumset.Leave;
//import com.elf.crud.enumset.Status;
//import com.elf.crud.enumset.Type;
//import com.fasterxml.jackson.annotation.JsonFormat;
//import jakarta.persistence.*;
//import lombok.*;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import java.time.LocalDate;
//import java.util.Date;
//import java.util.UUID;
//
//@Entity
//@Table(name = "lrs_leave_t")
//@NoArgsConstructor
//@AllArgsConstructor
//@Data
//@Builder
//public class LeaveEntity {
//    @Id
//    @GeneratedValue (strategy = GenerationType.UUID)
//    private UUID id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "staffId", nullable = false)
//    private StaffEntity staff;
//
////    @Temporal(TemporalType.DATE)
//    @JsonFormat(pattern = "dd-MM-yyyy")
//    private LocalDate startDate;
//
////    @Temporal(TemporalType.DATE)
//    @JsonFormat(pattern = "dd-MM-yyyy")
//    private LocalDate endDate;
//
//    @Enumerated(EnumType.STRING)
//    private Leave leaveType;
//
//    @Enumerated(EnumType.STRING)
//    private Type dateType;
//    private float leaveTaken;
//
//    @Enumerated(EnumType.STRING)
//    private Status status;
//    private String remarks;
//
//    @CreationTimestamp
//    private Date createdAt;
//
//    @UpdateTimestamp
//    private Date updatedAt;
//
//    @OneToOne(mappedBy = "leave", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
//    private AttachmentEntity attachment;
//
//
//}
