package dev.manage_fresher_app.controller;

import dev.manage_fresher_app.DTO.Request.Fresher.ChangePasswordRequest;
import dev.manage_fresher_app.entities.Fresher;
import dev.manage_fresher_app.service.FresherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/freshers")
@Validated
public class FresherController {
    @Autowired
    private FresherService fresherService;

    @GetMapping("/{id}")
    public Fresher getFresherById(@PathVariable Long id) {
        return fresherService.getFresherById(id);
    }

    // show list Fresher
    public List<Fresher> getAllFreshers() {
        return fresherService.getAllFreshers();
    }

    //Create Fresher
    @PostMapping
    public Fresher createFresher(@RequestBody Fresher fresher) {
        return fresherService.createFresher(fresher);
    }

    // Upadate Fresher information
    @PutMapping("/{id}")
    public Fresher updateFresher(@PathVariable Long id, @RequestBody Fresher fresherDetails){
        return fresherService.updateFresher(id, fresherDetails);
    }

    // Delete Fresher
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFresher(@PathVariable Long id){
        fresherService.deleteFresher(id);
        return ResponseEntity.ok().build();
    }

    // Search Fresher by name, email, course name
    @GetMapping("/searchFreshers")
    public List<Fresher> searchFreshers(@RequestParam(required = false) String name,
                                        @RequestParam(required = false) String email,
                                        @RequestParam(required = false) String courseName) {
        return fresherService.searchFreshers(name,email,courseName);
    }

    // Fresher change password
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        try {
            fresherService.changePassword(changePasswordRequest);
            return ResponseEntity.ok("Password changed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
