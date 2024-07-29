package dev.manage_fresher_app.repositories;

import dev.manage_fresher_app.entities.Fresher;
import dev.manage_fresher_app.entities.WorkingHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkingHistoryRepository extends JpaRepository<WorkingHistory, Long> {
    List<WorkingHistory> findByCenterId(Long centerId);
    Optional<WorkingHistory> findByFresherAndStatus(Fresher fresher, String status);
}
