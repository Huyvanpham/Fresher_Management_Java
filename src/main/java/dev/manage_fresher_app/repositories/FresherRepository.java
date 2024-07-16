package dev.manage_fresher_app.repositories;


import dev.manage_fresher_app.entities.Employee;
import dev.manage_fresher_app.entities.Fresher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FresherRepository extends JpaRepository<Fresher, Long> {
    @Query("SELECT f FROM Fresher f JOIN Employee e LEFT JOIN Course c WHERE " +
            "(:name IS NULL OR e.name LIKE %:name%) AND " +
            "(:email IS NULL OR e.email LIKE %:email%) AND " +
            "(:courseName IS NULL OR c.name LIKE %:courseName%)")
    List<Fresher> searchFreshers(@Param("name") String name,
                                 @Param("email") String email,
                                 @Param("courseName") String courseName);
}
