package testsystem.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import testsystem.backend.service.JwtService;
import testsystem.backend.service.ProfileService;

/**
 * Controller class for profile-related operations.
 */
@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private ProfileService profileService;

    /**
     * Returns the student's profile information based on the token header.
     *
     * @param token Authorization header containing the token.
     * @return ResponseEntity containing the student's profile information in the body or an error message if any exceptions occur.
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/get-student-info")
    public ResponseEntity<?> getStudentProfileInfo(
            @RequestHeader("Authorization") String token
    ) {
        try {
            return profileService.getStudentProfileInfo(
                    jwtService.extractUsername(token.replace("Bearer ", ""))
            );
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    /**
     * Returns the teacher's profile information based on the token header.
     *
     * @param token Authorization header containing the token.
     * @return ResponseEntity containing the teacher's profile information in the body or an error message if any exceptions occur.
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/get-teacher-info")
    public ResponseEntity<?> getTeacherProfileInfo(
            @RequestHeader("Authorization") String token
    ) {
        try {
            return profileService.getTeacherProfileInfo(
                    jwtService.extractUsername(token.replace("Bearer ", ""))
            );
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    /**
     * Returns the user role based on the token header.
     *
     * @param token Authorization header containing the token.
     * @return ResponseEntity containing a user role in the body or an error message if any exceptions occur.
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/get-role")
    public ResponseEntity<?> getUserRole(
            @RequestHeader("Authorization") String token
    ) {
        try {
            return profileService.getUserRole(
                    jwtService.extractUsername(token.replace("Bearer ", ""))
            );
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
