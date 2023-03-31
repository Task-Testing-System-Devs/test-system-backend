package testsystem.backend.repository.education;

import org.springframework.data.jpa.repository.JpaRepository;
import testsystem.backend.model.education.EdGroupConn;

import java.util.Optional;

public interface EdGroupConnRepository extends JpaRepository<EdGroupConn, Integer> {

    Optional<EdGroupConn> findEdGroupConnByDepartmentId(Integer departmentId);

}
