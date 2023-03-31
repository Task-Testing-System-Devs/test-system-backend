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

    @GetMapping("/get-info")
    public ResponseEntity<?> getProfileInfo(
            @RequestHeader("Authorization") String token
    ) {
        try {
            return profileService.getProfileInfo(jwtService.extractUsername(token.replace("Bearer ", "")));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

}
