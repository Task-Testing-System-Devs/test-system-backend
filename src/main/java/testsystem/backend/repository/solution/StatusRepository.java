package testsystem.backend.repository.solution;

import org.springframework.data.jpa.repository.JpaRepository;
import testsystem.backend.model.solution.Status;

/**
 * Repository corresponding to Status model and same table in database.
 */
public interface StatusRepository extends JpaRepository<Status, Integer> {
}
