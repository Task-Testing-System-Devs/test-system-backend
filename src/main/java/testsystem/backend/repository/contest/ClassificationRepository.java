package testsystem.backend.repository.contest;

import org.springframework.data.jpa.repository.JpaRepository;
import testsystem.backend.model.contest.Classification;

/**
 * Repository corresponding to Classification model and same table in database.
 */
public interface ClassificationRepository extends JpaRepository<Classification, Integer> {
}
