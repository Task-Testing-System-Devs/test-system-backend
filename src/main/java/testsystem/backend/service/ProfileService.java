package testsystem.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import testsystem.backend.dto.UserProfileInfoResponse;
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

import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DepartmentConnRepository departmentConnRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private EdGroupConnRepository edGroupConnRepository;
    @Autowired
    private EdGroupRepository edGroupRepository;

    public ResponseEntity<?> getProfileInfo(String email) {
        // Extract user main info.
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("No user with email: <" + email + "> found");
        }

        // Extract user personal info.
        Optional<UserInfo> userInfo = userInfoRepository.getUserInfoByUserId(user.get().getId());
        if (userInfo.isEmpty()) {
            return ResponseEntity.badRequest().body("No user personal info with email: <" + email + "> found");
        }

        // Setup department connection with user.
        Optional<DepartmentConn> depConn = departmentConnRepository.findDepartmentConnByUserId(user.get().getId());
        if (depConn.isEmpty()) {
            return ResponseEntity.badRequest().body("User with email: <" + email + "> has no department");
        }

        // Extract department info.
        Optional<Department> department = departmentRepository.findById(depConn.get().getDepartmentId());
        if (department.isEmpty()) {
            return ResponseEntity.badRequest().body("User with email: <" + email + "> has no department name");
        }

        // Setup education group connection with department.
        Optional<EdGroupConn> edGroupConn = edGroupConnRepository.findEdGroupConnByDepartmentId(department.get().getId());
        if (edGroupConn.isEmpty()) {
            return ResponseEntity.badRequest().body("User with email: <" + email + "> has no education group");
        }

        // Extract education group info.
        Optional<EdGroup> edGroup = edGroupRepository.findById(edGroupConn.get().getEdGroupId());
        if (edGroup.isEmpty()) {
            return ResponseEntity.badRequest().body("User with email: <" + email + "> has no education name");
        }

        // Build response about user profile info.
        UserProfileInfoResponse profileInfo = UserProfileInfoResponse.builder()
                .id(user.get().getId())
                .email(user.get().getEmail())
                .first_name(userInfo.get().getFirstName())
                .last_name(userInfo.get().getLastName())
                .middle_name(userInfo.get().getMiddleName())
                .department(department.get().getTitle())
                .group(edGroup.get().getTitle())
                .build();
        return ResponseEntity.ok(profileInfo);
    }
}
