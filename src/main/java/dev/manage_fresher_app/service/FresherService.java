package dev.manage_fresher_app.service;

import dev.manage_fresher_app.DTO.Request.Fresher.ChangePasswordRequest;
import dev.manage_fresher_app.DTO.Response.Fresher.FresherScoreStatisticsDTO;
import dev.manage_fresher_app.DTO.Response.Fresher.FresherStatisticsDTO;
import dev.manage_fresher_app.entities.ExerciseResult;
import dev.manage_fresher_app.entities.Fresher;

import java.util.Date;
import java.util.List;

public interface FresherService {
    Fresher getFresherById(Long id);

    List<Fresher> getAllFreshers();

    Fresher createFresher(Fresher fresher);

    Fresher updateFresher(Long id, Fresher fresherDetails);

    void deleteFresher(Long id);

    List<Fresher> searchFreshers(String name, String email, String courseName);

    void changePassword(ChangePasswordRequest changePasswordRequest) throws Exception;
    long countAllFreshers();
    Fresher moveFresherToNewCenter(Long fresherId, Long newCenterId);
    void caculateAndSaveExerciseResult(Long fresherId, Long exerciseId, double score, String feedback);
    List<FresherStatisticsDTO> getFresherStatisticsByCenter(Date startDate, Date endDate);
    List<FresherScoreStatisticsDTO> getFresherStatisticsByScore();
    public List<ExerciseResult> addExerciseResults(Long fresherId, List<ExerciseResult> exerciseResults);
}
