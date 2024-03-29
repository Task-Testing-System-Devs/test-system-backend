package testsystem.backend.repository.contest;

import org.springframework.data.jpa.repository.JpaRepository;
import testsystem.backend.model.contest.TaskConn;

import java.util.List;

/**
 * Repository corresponding to TaskConn model and same table in database.
 */
public interface TaskConnRepository extends JpaRepository<TaskConn, Integer> {
    List<TaskConn> findAllByContestId(Integer id);

}
