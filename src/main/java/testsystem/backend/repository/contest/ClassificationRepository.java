package testsystem.backend.repository.contest;

import org.springframework.data.jpa.repository.JpaRepository;
import testsystem.backend.model.contest.Classification;

public interface ClassificationRepository extends JpaRepository<Classification, Integer> {
}
