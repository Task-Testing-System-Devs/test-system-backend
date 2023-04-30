package testsystem.backend.repository.solution;

import org.springframework.data.jpa.repository.JpaRepository;
import testsystem.backend.model.solution.Language;

public interface LanguageRepository extends JpaRepository<Language, Integer> {
}
