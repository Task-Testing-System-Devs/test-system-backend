package testsystem.backend.repository.contest;

import org.springframework.data.jpa.repository.JpaRepository;
import testsystem.backend.model.contest.Task;

import java.util.Optional;

/**
 * Repository corresponding to Task model and same table in database.
 */
public interface TaskRepository extends JpaRepository<Task, Integer> {

    Optional<Task> findByTitle(String title);

}
