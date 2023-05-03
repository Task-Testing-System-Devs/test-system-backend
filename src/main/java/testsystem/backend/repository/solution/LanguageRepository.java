package testsystem.backend.repository.solution;

import org.springframework.data.jpa.repository.JpaRepository;
import testsystem.backend.model.solution.Language;

/**
 * Repository corresponding to Language model and same table in database.
 */
public interface LanguageRepository extends JpaRepository<Language, Integer> {
}
