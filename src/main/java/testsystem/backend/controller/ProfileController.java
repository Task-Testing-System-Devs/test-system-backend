package testsystem.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import testsystem.backend.service.JwtService;
import testsystem.backend.service.ProfileService;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private ProfileService profileService;

    @GetMapping("/get-student-info")
    public ResponseEntity<?> getStudentProfileInfo(
            @RequestHeader("Authorization") String token
    ) {
        try {
            return profileService.getStudentProfileInfo(jwtService.extractUsername(token.replace("Bearer ", "")));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @GetMapping("/get-teacher-info")
    public ResponseEntity<?> getTeacherProfileInfo(
            @RequestHeader("Authorization") String token
    ) {
        try {
            return profileService.getTeacherProfileInfo(jwtService.extractUsername(token.replace("Bearer ", "")));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

}
