package dev.manage_fresher_app.repositories;

import dev.manage_fresher_app.entities.ExerciseResult;
import dev.manage_fresher_app.entities.Fresher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseResultRepository extends JpaRepository<ExerciseResult, Long> {
    List<ExerciseResult> findByFresher(Fresher fresher);
}
