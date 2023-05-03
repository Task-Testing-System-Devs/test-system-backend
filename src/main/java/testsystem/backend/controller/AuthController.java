package testsystem.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import testsystem.backend.dto.AuthRequest;
import testsystem.backend.dto.LoginResponse;
import testsystem.backend.dto.UserRegisterRequest;
import testsystem.backend.service.AuthService;
import testsystem.backend.service.JwtService;

/**
 * Controller class for authentication-related operations.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Registers a new user to the system.
     *
     * @param user DTO containing the details of the new user to be registered.
     * @return HTTP response indicating the outcome of the registration request.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @RequestBody UserRegisterRequest user
    ) {
        try {
            // Forward the registration request to the AuthService.
            return authService.register(user);
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.badRequest()
                    .body("Occurred error during creating and setting user with following message: " +
                            exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    /**
     * Authenticates a user and generates a JWT token for them.
     *
     * @param authRequest DTO containing the email and password of the user to be authenticated.
     * @return HTTP response containing the generated JWT token and the user's role.
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateAndGetToken(
            @RequestBody AuthRequest authRequest
    ) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(),
                            authRequest.getPassword()
                    )
            );
            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(authRequest.getEmail());
                String role = authService.getRole(authRequest.getEmail());

                // Build and return a LoginResponse containing the generated JWT token and the user's role.
                return ResponseEntity.ok(
                        LoginResponse.builder()
                                .token(token)
                                .role(role)
                                .build()
                );
            }
            return ResponseEntity.badRequest().body("Invalid user request");
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
