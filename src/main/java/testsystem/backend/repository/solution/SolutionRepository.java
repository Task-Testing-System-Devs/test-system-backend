package testsystem.backend.repository.solution;

import org.springframework.data.jpa.repository.JpaRepository;
import testsystem.backend.model.solution.Solution;

public interface SolutionRepository extends JpaRepository<Solution, Integer> {
}
