package testsystem.backend.repository.contest;

import org.springframework.data.jpa.repository.JpaRepository;
import testsystem.backend.model.contest.UniqueContestTask;

public interface UniqueContestTaskRepository extends JpaRepository<UniqueContestTask, Integer> {
}
