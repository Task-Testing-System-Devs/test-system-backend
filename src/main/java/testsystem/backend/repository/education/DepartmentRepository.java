package testsystem.backend.repository.education;

import org.springframework.data.jpa.repository.JpaRepository;
import testsystem.backend.model.education.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
