package testsystem.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import testsystem.backend.dto.TeacherRegisterRequest;
import testsystem.backend.model.user.User;
import testsystem.backend.model.user.UserInfo;
import testsystem.backend.repository.user.UserInfoRepository;
import testsystem.backend.repository.user.UserRepository;

/**
 * This class provides the implementation for admin service.
 */
@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * This method creates a new teacher and saves all info to the "users" and "users_info" tables.
     *
     * @param newTeacher - Information about the new teacher to be registered.
     * @return ResponseEntity with a message about the result of the teacher registration.
     */
    @Transactional
    public ResponseEntity<?> addTeacher(TeacherRegisterRequest newTeacher) {
        if (userRepository.findByEmail(newTeacher.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Teacher with such email address already exists");
        }

        // Encode password.
        newTeacher.setPassword(passwordEncoder.encode(newTeacher.getPassword()));

        // Insert into "users" table.
        User newUser = User.builder()
                .role(newTeacher.getRole())
                .email(newTeacher.getEmail())
                .password(newTeacher.getPassword())
                .build();
        userRepository.save(newUser);

        // Insert into "users_info" table.
        UserInfo userInfo = UserInfo.builder()
                .userId(newUser.getId())
                .firstName(newTeacher.getFirst_name())
                .lastName(newTeacher.getLast_name())
                .middleName(newTeacher.getMiddle_name())
                .build();
        userInfoRepository.save(userInfo);

        return ResponseEntity.status(HttpStatus.CREATED).body("New teacher was successfully created and registered");
    }
}
