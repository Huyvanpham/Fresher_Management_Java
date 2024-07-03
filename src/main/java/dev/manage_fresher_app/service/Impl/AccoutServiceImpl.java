package dev.manage_fresher_app.service.Impl;

import dev.manage_fresher_app.DTO.Request.LoginRequest;
import dev.manage_fresher_app.entities.Account;
import dev.manage_fresher_app.repositories.AccountRepository;
import dev.manage_fresher_app.repositories.EmployeeRepository;
import dev.manage_fresher_app.service.AccountService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccoutServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Account login(LoginRequest loginRequestDTO) {
        return accountRepository.findByEmail(loginRequestDTO.getEmail())
                .filter(account -> passwordEncoder.matches(loginRequestDTO.getPassword(), account.getPassword()))
                .orElseThrow(() -> new UsernameNotFoundException("Invalid email or password"));
    }
}
