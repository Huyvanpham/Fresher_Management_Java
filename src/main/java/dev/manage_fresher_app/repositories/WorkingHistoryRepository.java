package dev.manage_fresher_app.repositories;

import dev.manage_fresher_app.DTO.Response.Fresher.FresherScoreStatisticsDTO;
import dev.manage_fresher_app.DTO.Response.Fresher.FresherStatisticsDTO;
import dev.manage_fresher_app.entities.Fresher;
import dev.manage_fresher_app.entities.WorkingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface WorkingHistoryRepository extends JpaRepository<WorkingHistory, Long> {
    List<WorkingHistory> findByCenterId(Long centerId);
    Optional<WorkingHistory> findByFresherAndStatus(Fresher fresher, String status);

    @Query("SELECT new dev.manage_fresher_app.DTO.Response.Fresher.FresherStatisticsDTO(c.name, COUNT(f.id)) " +
            "FROM WorkingHistory wh " +
            "JOIN Fresher f ON wh.fresher.id = f.id " +
            "JOIN wh.center c " +
            "WHERE wh.startTime >= :startDate AND wh.endTime <= :endDate " +
            "GROUP BY c.name " +
            "ORDER BY c.name")
    List<FresherStatisticsDTO> findFresherStatisticsByCenter(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT new dev.manage_fresher_app.DTO.Response.Fresher.FresherScoreStatisticsDTO(f.avgScore, COUNT(f.id)) " +
            "FROM Fresher f " +
            "GROUP BY f.avgScore " +
            "ORDER BY f.avgScore")
    List<FresherScoreStatisticsDTO> findFresherStatisticsByScore();
}
