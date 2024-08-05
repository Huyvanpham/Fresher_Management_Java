package dev.manage_fresher_app.controller;

import dev.manage_fresher_app.DTO.Request.LoginRequest;
import dev.manage_fresher_app.entities.Account;
import dev.manage_fresher_app.repositories.AccountRepository;
import dev.manage_fresher_app.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;
    private final PasswordEncoder encoder;
    private final AccountRepository repository;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(accountService.login(loginRequest));
    }

    @GetMapping("/test")
    public String test(){
        List<Account> accounts = repository.findAll();
        accounts.forEach(account -> {
            account.setPassword(encoder.encode(account.getPassword()));
        });
        repository.saveAll(accounts);
        return "oik";
    }
}
