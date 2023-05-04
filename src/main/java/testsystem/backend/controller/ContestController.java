package testsystem.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import testsystem.backend.dto.ContestRequest;
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
    @GetMapping("/add")
    public ResponseEntity<?> getContestInfo(
            @RequestHeader("Authorization") String token,
            @RequestBody ContestRequest contestRequest
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
}
