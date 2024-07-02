package dev.manage_fresher_app.controller;

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

    //register

    //login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Account account) {
        Optional<Account> loggedInAccount = accountService.login(account.getEmail(), account.getPassword());
        if(loggedInAccount.isPresent()) {
            return ResponseEntity.ok(loggedInAccount);
        } else {
            return ResponseEntity.status(401).body("Email hoặc mật khẩu không đúng");
        }
    }
}
