package com.elf.crud.token;

import com.elf.crud.entity.StaffEntity;
import com.elf.crud.enumset.TokenType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

@Entity
@Table(name = "token")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Token {
    @Id
    @GeneratedValue
    private Integer id;
    private String token;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;
    private boolean expired;
    private boolean revoked;


    @JoinColumn(name = "staff_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private StaffEntity staff;

    private String email;
}
