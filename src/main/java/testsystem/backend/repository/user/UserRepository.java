package testsystem.backend.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import testsystem.backend.model.user.User;

import java.util.Optional;

/**
 * Repository corresponding to User model and same table in database.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

}
