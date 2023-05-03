package testsystem.backend.repository.education;

import org.springframework.data.jpa.repository.JpaRepository;
import testsystem.backend.model.education.Department;

/**
 * Repository corresponding to Department model and same table in database.
 */
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
