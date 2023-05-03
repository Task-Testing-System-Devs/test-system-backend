package testsystem.backend.repository.education;

import org.springframework.data.jpa.repository.JpaRepository;
import testsystem.backend.model.education.EdGroup;

/**
 * Repository corresponding to EdGroup model and same table in database.
 */
public interface EdGroupRepository extends JpaRepository<EdGroup, Integer> {
}
