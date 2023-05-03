package testsystem.backend.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import testsystem.backend.model.user.UserInfo;

import java.util.Optional;

/**
 * Repository corresponding to UserInfo model and same table in database.
 */
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

    Optional<UserInfo> getUserInfoByUserId(Integer userId);

}
