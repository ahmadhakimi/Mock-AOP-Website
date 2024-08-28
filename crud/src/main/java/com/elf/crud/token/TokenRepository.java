package com.elf.crud.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query("SELECT t FROM Token t " +
            "INNER JOIN t.staff s " +
            "WHERE s.id = :id AND (t.expired = false OR t.revoked = false)")
    List<Token> findAllValidTokenByUser(String id);


    Optional<Token> findByToken(String token);

}
