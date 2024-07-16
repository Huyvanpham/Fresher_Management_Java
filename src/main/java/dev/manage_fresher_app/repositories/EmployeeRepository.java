package dev.manage_fresher_app.repositories;

import dev.manage_fresher_app.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Employee > {
}
