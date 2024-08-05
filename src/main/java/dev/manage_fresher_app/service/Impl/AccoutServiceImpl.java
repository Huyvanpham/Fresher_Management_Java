package dev.manage_fresher_app.service.Impl;

import dev.manage_fresher_app.DTO.Request.LoginRequest;
import dev.manage_fresher_app.entities.Account;
import dev.manage_fresher_app.repositories.AccountRepository;
import dev.manage_fresher_app.repositories.EmployeeRepository;
import dev.manage_fresher_app.service.AccountService;
import dev.manage_fresher_app.service.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccoutServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public String login(LoginRequest loginRequestDTO) {
        Account account = accountRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid email or password"));

        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(account.getUsername(), account.getPassword()));
        } catch (AuthenticationException e) {

        }
        return jwtService.generateToken(account);
    }
}
