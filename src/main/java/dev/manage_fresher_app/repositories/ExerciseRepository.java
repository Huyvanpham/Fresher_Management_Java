package dev.manage_fresher_app.repositories;

import dev.manage_fresher_app.entities.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

}
