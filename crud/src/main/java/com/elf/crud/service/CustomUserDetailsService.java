package com.elf.crud.service;

import com.elf.crud.entity.StaffEntity;
import com.elf.crud.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final StaffRepository staffRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return staffRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Staff not found with email: " + email));
    }
}
