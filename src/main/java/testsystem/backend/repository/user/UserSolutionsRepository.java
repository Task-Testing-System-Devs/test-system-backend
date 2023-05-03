package testsystem.backend.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import testsystem.backend.model.user.UserSolution;

import java.util.List;
import java.util.Optional;

/**
 * Repository corresponding to UserSolution model and same table in database.
 */
public interface UserSolutionsRepository extends JpaRepository<UserSolution, Integer> {

    List<Optional<UserSolution>> findAllByUserId(Integer id);

}
