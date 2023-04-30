package testsystem.backend.repository.solution;

import org.springframework.data.jpa.repository.JpaRepository;
import testsystem.backend.model.solution.Status;

public interface StatusRepository extends JpaRepository<Status, Integer> {
}
