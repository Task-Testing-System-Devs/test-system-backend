package testsystem.backend.repository.solution;

import org.springframework.data.jpa.repository.JpaRepository;
import testsystem.backend.model.solution.Solution;

/**
 * Repository corresponding to Solution model and same table in database.
 */
public interface SolutionRepository extends JpaRepository<Solution, Integer> {
}
