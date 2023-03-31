package testsystem.backend.repository.education;

import org.springframework.data.jpa.repository.JpaRepository;
import testsystem.backend.model.education.EdGroup;

public interface EdGroupRepository extends JpaRepository<EdGroup, Integer> {
}
