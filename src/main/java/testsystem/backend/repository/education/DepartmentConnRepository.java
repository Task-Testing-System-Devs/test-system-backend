package testsystem.backend.repository.education;

import org.springframework.data.jpa.repository.JpaRepository;
import testsystem.backend.model.education.DepartmentConn;

import java.util.Optional;

/**
 * Repository corresponding to DepartmentConn model and same table in database.
 */
public interface DepartmentConnRepository extends JpaRepository<DepartmentConn, Integer> {

    Optional<DepartmentConn> findDepartmentConnByUserId(Integer userId);

}
