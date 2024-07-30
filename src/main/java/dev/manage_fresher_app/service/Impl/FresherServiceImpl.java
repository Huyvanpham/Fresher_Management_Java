package dev.manage_fresher_app.service.Impl;

import dev.manage_fresher_app.DTO.Request.Fresher.ChangePasswordRequest;
import dev.manage_fresher_app.DTO.Response.Fresher.FresherScoreStatisticsDTO;
import dev.manage_fresher_app.DTO.Response.Fresher.FresherStatisticsDTO;
import dev.manage_fresher_app.entities.*;
import dev.manage_fresher_app.exceptions.ResourceNotFoundException;
import dev.manage_fresher_app.repositories.*;
import dev.manage_fresher_app.service.FresherService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FresherServiceImpl implements FresherService {
    private final FresherRepository fresherRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private WorkingHistoryRepository workingHistoryRepository;

    @Autowired
    private CenterRepository centerRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private ExerciseResultRepository exerciseResultRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // show Fresher by Id
    @Override
    public Fresher getFresherById(Long id) {
        return fresherRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Fresher not found with id " + id));
    }

    // show list Fresher
    @Override
    public List<Fresher> getAllFreshers() {
        return fresherRepository.findAll();
    }

    // Create Fresher
    @Override
    public Fresher createFresher(Fresher fresher) {
        return fresherRepository.save(fresher);
    }

    // Update Fresher information
    @Override
    public Fresher updateFresher(Long id, Fresher fresherDetails) {
        Fresher fresher = fresherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fresher not found with id " + id));

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
                .orElseThrow(() -> new RuntimeException("Fresher not found with id " + id));
        fresherRepository.delete(fresher);
    }

    @Override
    public List<Fresher> searchFreshers(String name, String email, String courseName) {
        return fresherRepository.searchFreshers(name, email, courseName);
    }

    @Override
    public void changePassword(ChangePasswordRequest changePasswordRequest) throws Exception {
        //Todo: Thêm Chức nàng security
        // lấy thông tin người dùng hiện tại
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Account account = accountRepository.findByEmail(currentUsername)
                .orElseThrow(() -> new Exception("User not found"));

        // kiểm tra mật khẩu hiện tại
        if (!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), account.getPassword())) {
            throw new Exception("Current password is incorrect");
        }

        // kiểm tra mật khẩu xác nhận
        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
            throw new Exception("New password and confirmation password do not match");
        }

        // Đổi mật khẩu
        account.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        accountRepository.save(account);
    }

    // dem so luong fresher
    @Override
    public long countAllFreshers() {
        return fresherRepository.count();
    }

    //di chuyen fresher
    @Override
    public Fresher moveFresherToNewCenter(Long fresherId, Long newCenterId) {
        Fresher fresher = fresherRepository.findById(fresherId)
                .orElseThrow(() -> new ResourceNotFoundException("Fresher not found"));
        Center newCenter = centerRepository.findById(newCenterId)
                .orElseThrow(() -> new ResourceNotFoundException("center not found"));

        //  ket thuc lich su lam viec hien tai
        WorkingHistory currentWorkingHistory = workingHistoryRepository.findByFresherAndStatus(fresher, "active")
                .orElseThrow(() -> new ResourceNotFoundException("Active working history not found"));
        currentWorkingHistory.setEndTime(new Date(new java.util.Date().getTime()));
        currentWorkingHistory.setStatus("inactive");
        workingHistoryRepository.save(currentWorkingHistory);

        // Tạo lịch sử làm việc mới
        WorkingHistory newWorkingHistory = new WorkingHistory();
        newWorkingHistory.setFresher(fresher);
        newWorkingHistory.setCenter(newCenter);
        newWorkingHistory.setStartTime(new Date(new java.util.Date().getTime()));
        newWorkingHistory.setStatus("active");

        workingHistoryRepository.save(newWorkingHistory);

        return fresher;
    }

    // tinh diem cho fresher
    @Override
    public void caculateAndSaveExerciseResult(Long fresherId, Long exerciseId, double score, String feedback) {
        ExerciseResult exerciseResult = new ExerciseResult();
        Fresher fresher = fresherRepository.findById(fresherId).orElseThrow(() -> new RuntimeException("Fresher not found"));

        exerciseResult.setFresher(fresher);
        exerciseResult.setExercise(new Exercise());
        exerciseResult.setExercise(exerciseRepository.findById(exerciseId).orElseThrow(() -> new RuntimeException("Exercise not found")));
        exerciseResult.setScore(score);
        exerciseResult.setFeedback(feedback);

        exerciseResultRepository.save(exerciseResult);

        List<ExerciseResult> results = exerciseResultRepository.findByFresher(fresher);
        Double avgScore = results.stream().mapToDouble(ExerciseResult::getScore).average().orElse(0.0);

        fresher.setAvgScore(avgScore);
        fresherRepository.save(fresher);
    }

    // thong ke so luong fresher theo trung tam
    @Override
    public List<FresherStatisticsDTO> getFresherStatisticsByCenter(java.util.Date startDate, java.util.Date endDate) {
        return workingHistoryRepository.findFresherStatisticsByCenter(startDate, endDate);
    }

    // thong ke so lương fresher theo diem so
    @Override
    public List<FresherScoreStatisticsDTO> getFresherStatisticsByScore() {
        return workingHistoryRepository.findFresherStatisticsByScore();
    }

    @Transactional
    @Override
    public List<ExerciseResult> addExerciseResults(Long fresherId, List<ExerciseResult> exerciseResults) {
        Fresher fresher = fresherRepository.findById(fresherId).orElseThrow(() -> new RuntimeException("Fresher not found"));

        if(exerciseResults.isEmpty())
            throw new RuntimeException("Kết quả làm bài không được trống");

        for (ExerciseResult result : exerciseResults) {
            WorkingHistory workingHistory = workingHistoryRepository.findById(result.getHistory().getId())
                    .orElseThrow(() -> new RuntimeException("WorkingHistory not found"));

            Exercise exercise = exerciseRepository.findById(result.getExercise().getId()).orElseThrow(() -> new RuntimeException("Bài tập không tồn tại"));

            result.setFresher(fresher);
            result.setExercise(exercise);
            result.setScore(result.getScore());
            result.setFeedback(result.getFeedback());
            result.setHistory(workingHistory);
        }
        double avgScore = exerciseResults.stream().mapToDouble(ExerciseResult::getScore).average().orElse(0.0);
        fresher.setAvgScore(avgScore);
        fresherRepository.save(fresher);
        List<ExerciseResult> resultSaved = exerciseResultRepository.saveAll(exerciseResults);
        resultSaved.forEach(result -> {
            result.getExercise().setExerciseResults(null);
            result.setHistory(null);
            result.setFresher(null);
        });
        return resultSaved;
    }
}