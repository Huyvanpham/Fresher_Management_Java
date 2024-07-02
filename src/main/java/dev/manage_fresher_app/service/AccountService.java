package dev.manage_fresher_app.service;

import dev.manage_fresher_app.entities.Account;
import dev.manage_fresher_app.entities.Employee;
import dev.manage_fresher_app.repositories.AccountRepository;
import dev.manage_fresher_app.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // register

    //login
    public Optional<Account> login(String email, String password) {
        Optional<Account> account = accountRepository.findByEmail(email);
        if(account.isPresent() && bCryptPasswordEncoder.matches(password, account.get().getPassword())){
            return account;
        }
        return Optional.empty();
    }
}
