package testsystem.backend.repository.contest;

import org.springframework.data.jpa.repository.JpaRepository;
import testsystem.backend.model.contest.Contest;
import testsystem.backend.model.contest.ContestConn;

import java.util.List;

public interface ContestConnRepository extends JpaRepository<ContestConn, Integer> {
    List<ContestConn> findAllByUserId(Integer id);
}
