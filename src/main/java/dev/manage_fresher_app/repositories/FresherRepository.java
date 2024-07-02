package dev.manage_fresher_app.repositories;


import dev.manage_fresher_app.entities.Employee;
import dev.manage_fresher_app.entities.Fresher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FresherRepository extends JpaRepository<Fresher, Long> {

}
