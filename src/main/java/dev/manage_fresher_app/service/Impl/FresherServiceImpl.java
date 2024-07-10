package dev.manage_fresher_app.service.Impl;

import dev.manage_fresher_app.DTO.Request.Fresher.ChangePasswordRequest;
import dev.manage_fresher_app.entities.Account;
import dev.manage_fresher_app.entities.Fresher;
import dev.manage_fresher_app.repositories.AccountRepository;
import dev.manage_fresher_app.repositories.FresherRepository;
import dev.manage_fresher_app.service.FresherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.Authenticator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FresherServiceImpl implements FresherService {
    private final FresherRepository fresherRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // show Fresher by Id
    @Override
    public Fresher getFresherById(Long id) {
        return fresherRepository.findById(id).orElseThrow(() -> new RuntimeException("Fresher not found with id " + id));
    }

    // show list Fresher
    @Override
    public List<Fresher> getAllFreshers() {
        return fresherRepository.findAll();
    }

    // Create Fresher
    @Override
    public Fresher createFresher(Fresher fresher){
        return fresherRepository.save(fresher);
    }

    // Update Fresher information
    @Override
    public Fresher updateFresher(Long id, Fresher fresherDetails) {
        Fresher fresher = fresherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fresher not found with id "+ id));

        fresher.setStatus(fresherDetails.getStatus());

        // Update Employee information
        fresher.setName(fresherDetails.getName());
        fresher.setDateOfBirth(fresherDetails.getDateOfBirth());
        fresher.setSex(fresherDetails.getSex());
        fresher.setPhoneNumber(fresherDetails.getPhoneNumber());
        fresher.setEmail(fresherDetails.getEmail());
        fresher.setStatus(fresherDetails.getStatus());

        return fresherRepository.save(fresher);
    }

    // Delete Fresher
    @Override
    public void deleteFresher(Long id) {
        Fresher fresher = fresherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fresher not found with id "+ id));
        fresherRepository.delete(fresher);
    }

    @Override
    public List<Fresher> searchFreshers(String name, String email, String courseName) {
        return fresherRepository.searchFreshers(name,email,courseName);
    }

    @Override
    public void changePassword(ChangePasswordRequest changePasswordRequest) throws Exception {
        // lấy thông tin người dùng hiện tại
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Account account = accountRepository.findByEmail(currentUsername)
                .orElseThrow(() -> new Exception("User not found"));

        // kiểm tra mật khẩu hiện tại
        if(!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(),account.getPassword())){
            throw new Exception("Current password is incorrect");
        }

        // kiểm tra mật khẩu xác nhận
        if(!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
            throw new Exception("New password and confirmation password do not match");
        }

        // Đổi mật khẩu
        account.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        accountRepository.save(account);
    }


}
