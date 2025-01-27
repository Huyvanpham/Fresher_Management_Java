package dev.manage_fresher_app.controller;

import dev.manage_fresher_app.DTO.Request.Fresher.ChangePasswordRequest;
import dev.manage_fresher_app.DTO.Response.Fresher.FresherScoreStatisticsDTO;
import dev.manage_fresher_app.DTO.Response.Fresher.FresherStatisticsDTO;
import dev.manage_fresher_app.entities.ExerciseResult;
import dev.manage_fresher_app.entities.Fresher;
import dev.manage_fresher_app.service.FresherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
    @GetMapping
    public List<Fresher> getAllFreshers() {
        return fresherService.getAllFreshers();
    }

    @GetMapping("/count")
    public long countAllFreshers(){
        return fresherService.countAllFreshers();
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

    // di chuyen fresher
    @PostMapping("/{fresherId}/move-to-center/{newCenterId}")
    public ResponseEntity<Fresher> moveFresherToNewCenter(
            @PathVariable Long fresherId, @PathVariable Long newCenterId){
        Fresher updatedFresher = fresherService.moveFresherToNewCenter(fresherId,newCenterId);
        return ResponseEntity.ok(updatedFresher);
    }

    // tinh diem cho fresher
    @PostMapping("{fresherId}/exercise-results")
    public List<ExerciseResult> addExerciseResults( @PathVariable Long fresherId, @RequestBody List<ExerciseResult> exerciseResults) {
        return fresherService.addExerciseResults(fresherId, exerciseResults);
    }

    //thong ke so luong fresher theo tung trung tam
    @GetMapping("/statistics")
    public List<FresherStatisticsDTO> getFresherStatisticsByCenter(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        return fresherService.getFresherStatisticsByCenter(startDate, endDate);
    }

    //thong ke so luong fresher theo diem
    @GetMapping("/statistics/score")
    public List<FresherScoreStatisticsDTO> getFresherStatisticsByScore() {
        return fresherService.getFresherStatisticsByScore();
    }

}
