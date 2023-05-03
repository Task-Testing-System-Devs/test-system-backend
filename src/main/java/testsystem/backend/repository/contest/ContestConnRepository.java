package testsystem.backend.repository.contest;

import org.springframework.data.jpa.repository.JpaRepository;
import testsystem.backend.model.contest.ContestConn;

import java.util.List;

/**
 * Repository corresponding to ContestConn model and same table in database.
 */
public interface ContestConnRepository extends JpaRepository<ContestConn, Integer> {
    List<ContestConn> findAllByUserId(Integer id);
}
