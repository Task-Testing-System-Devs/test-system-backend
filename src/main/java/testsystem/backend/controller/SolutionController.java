package testsystem.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import testsystem.backend.dto.SolutionDTOObject;
import testsystem.backend.service.JwtService;
import testsystem.backend.service.SolutionService;

/**
 * Controller class for solutions-related operations.
 */
@RestController
@RequestMapping("/api/solutions")
@CrossOrigin(origins = "*")
public class SolutionController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private SolutionService solutionService;

    /**
     * Retrieves all user solutions.
     *
     * @param token Authorization header containing the token.
     * @return ResponseEntity with all retrieved user solutions in the response body.
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/get-all-user")
    public ResponseEntity<?> getUserSolutions(
            @RequestHeader("Authorization") String token
    ) {
        try {
            return solutionService.getUserSolutions(
                    jwtService.extractUsername(token.replace("Bearer ", ""))
            );
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    /**
     * Adds new user solution.
     *
     * @param solutionDTOObject DTO object containing the user's solution details.
     * @return ResponseEntity with the adding user solution operation status.
     */
    @CrossOrigin(origins = "*")
    @PostMapping("/add")
    public ResponseEntity<?> addUserSolution(
            @RequestHeader("Authorization") String token,
            @RequestBody SolutionDTOObject solutionDTOObject
            ) {
        try {
            return solutionService.addUserSolution(
                    jwtService.extractUsername(token.replace("Bearer ", "")),
                    solutionDTOObject
            );
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    /**
     * Retrieve all solutions for the authenticated user who has a teacher role.
     *
     * @param token Authorization header containing the token.
     * @return ResponseEntity containing a list of solutions or an error message.
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/get-all")
    public ResponseEntity<?> getAllSolutions(
            @RequestHeader("Authorization") String token
    ) {
        try {
            return solutionService.getAllSolutions(
                    jwtService.extractUsername(token.replace("Bearer ", ""))
            );
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
