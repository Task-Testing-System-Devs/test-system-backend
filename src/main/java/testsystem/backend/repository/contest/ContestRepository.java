package testsystem.backend.repository.contest;

import org.springframework.data.jpa.repository.JpaRepository;
import testsystem.backend.model.contest.Contest;

import java.util.Optional;

public interface ContestRepository extends JpaRepository<Contest, Integer> {

    Optional<Contest> findByTitle(String title);

}
