package dev.manage_fresher_app.service;

import dev.manage_fresher_app.DTO.Request.LoginRequest;
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
public interface AccountService {
    Account login(LoginRequest loginRequest);
}