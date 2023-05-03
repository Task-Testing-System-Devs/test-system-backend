package testsystem.backend.repository.contest;

import org.springframework.data.jpa.repository.JpaRepository;
import testsystem.backend.model.contest.UniqueContestTask;

/**
 * Repository corresponding to UniqueContestTask model and same table in database.
 */
public interface UniqueContestTaskRepository extends JpaRepository<UniqueContestTask, Integer> {
}
