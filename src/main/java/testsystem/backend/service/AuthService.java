package testsystem.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import testsystem.backend.dto.UserRegisterRequest;
import testsystem.backend.model.education.Department;
import testsystem.backend.model.education.DepartmentConn;
import testsystem.backend.model.education.EdGroup;
import testsystem.backend.model.education.EdGroupConn;
import testsystem.backend.model.user.User;
import testsystem.backend.model.user.UserInfo;
import testsystem.backend.repository.education.DepartmentConnRepository;
import testsystem.backend.repository.education.DepartmentRepository;
import testsystem.backend.repository.education.EdGroupConnRepository;
import testsystem.backend.repository.education.EdGroupRepository;
import testsystem.backend.repository.user.UserInfoRepository;
import testsystem.backend.repository.user.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private DepartmentConnRepository departmentConnRepository;
    @Autowired
    private EdGroupRepository edGroupRepository;
    @Autowired
    private EdGroupConnRepository edGroupConnRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseEntity<?> register(UserRegisterRequest user) throws DataIntegrityViolationException {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email address already in use");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Insert into "users" table.
        User newUser = User.builder()
                .role(user.getRole())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
        userRepository.save(newUser);

        // Insert into "users_info" table.
        UserInfo userInfo = UserInfo.builder()
                .userId(newUser.getId())
                .firstName(user.getFirst_name())
                .lastName(user.getLast_name())
                .middleName(user.getMiddle_name())
                .build();
        userInfoRepository.save(userInfo);

        // Insert into "departments" table.
        Department department = Department.builder()
                .title(user.getDepartment())
                .build();
        departmentRepository.save(department);

        // Insert into "departments_conn" table.
        DepartmentConn departmentConn = DepartmentConn.builder()
                .departmentId(department.getId())
                .userId(newUser.getId())
                .build();
        departmentConnRepository.save(departmentConn);

        // Insert into "ed_groups" table.
        EdGroup edGroup = EdGroup.builder()
                .title(user.getGroup())
                .build();
        edGroupRepository.save(edGroup);

        // Insert into "ed_groups_conn" table.
        EdGroupConn edGroupConn = EdGroupConn.builder()
                .edGroupId(edGroup.getId())
                .departmentId(department.getId())
                .build();
        edGroupConnRepository.save(edGroupConn);

        return ResponseEntity.ok("User was successfully created and registered");
    }

}
