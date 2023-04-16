package testsystem.backend.repository.contest;

import org.springframework.data.jpa.repository.JpaRepository;
import testsystem.backend.model.contest.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
