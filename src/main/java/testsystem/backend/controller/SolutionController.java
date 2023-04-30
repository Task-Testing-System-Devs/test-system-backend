package testsystem.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import testsystem.backend.dto.SolutionDTOObject;
import testsystem.backend.service.JwtService;
import testsystem.backend.service.SolutionService;

@RestController
@RequestMapping("/api/solutions")
public class SolutionController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private SolutionService solutionService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getUserSolutions(
            @RequestHeader("Authorization") String token
    ) {
        try {
            return solutionService.getUserSolutions(jwtService.extractUsername(token.replace("Bearer ", "")));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUserSolution(
            @RequestHeader("Authorization") String token,
            @RequestBody SolutionDTOObject solutionDTOObject
            ) {
        try {
            return solutionService.addUserSolution(jwtService.extractUsername(token.replace("Bearer ", "")), solutionDTOObject);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

}
