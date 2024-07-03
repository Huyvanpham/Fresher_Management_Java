package dev.manage_fresher_app.controller;

import dev.manage_fresher_app.DTO.Request.LoginRequest;
import dev.manage_fresher_app.entities.Account;
import dev.manage_fresher_app.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        accountService.login(loginRequest);
        return ResponseEntity.ok("Login successful");
    }
}
