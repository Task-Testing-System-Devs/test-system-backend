package testsystem.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import testsystem.backend.dto.ContestDTOObject;
import testsystem.backend.service.ContestService;
import testsystem.backend.service.JwtService;

/**
 * Controller class for handling HTTP requests related to contests.
 */
@RestController
@RequestMapping("/api/contest")
public class ContestController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private ContestService contestService;

    /**
     * Retrieves the contest information and adds it to the database.
     *
     * @param token          The token from the HTTP request header.
     * @param contestRequest The ContestRequest DTO that holds the contest information.
     * @return ResponseEntity A response that contains the result of the operation and the HTTP status.
     */
    @CrossOrigin(origins = "*")
    @PostMapping("/add")
    public ResponseEntity<?> addNewContest(
            @RequestHeader("Authorization") String token,
            @RequestBody ContestDTOObject contestRequest
            ) {
        try {
            return contestService.addContest(
                    jwtService.extractUsername(token.replace("Bearer ", "")),
                    contestRequest
            );
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    /**
     * This controller method gets all contests in which a specific user participates in.
     *
     * @param token The token from the HTTP request header.
     * @return ResponseEntity with a list of all the contests user participates in.
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/get-all-user")
    public ResponseEntity<?> getAllUserContests(
            @RequestHeader("Authorization") String token
    ) {
        try {
            return contestService.getAllUserContests(
                    jwtService.extractUsername(token.replace("Bearer ", ""))
            );
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    /**
     * This controller method gets all contests for all users.
     *
     * @return ResponseEntity with a list of all the contests in system for teacher to retrieve.
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/get-all")
    public ResponseEntity<?> getAllContests() {
        try {
            return contestService.getAllContests();
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/info")
    public ResponseEntity<?> getContestInfoById(
            @RequestParam("id") Integer id
    ) {
        try {
            return contestService.getContestInfoById(id);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
