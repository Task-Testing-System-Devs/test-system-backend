package testsystem.backend.controller;

import org.graalvm.polyglot.*;
import org.graalvm.polyglot.proxy.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import testsystem.backend.dto.ContestRequest;
import testsystem.backend.service.ContestService;
import testsystem.backend.service.JwtService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/contest")
public class ContestController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private ContestService contestService;

    @GetMapping("/add")
    public ResponseEntity<?> getContestInfo(
            @RequestHeader("Authorization") String token,
            @RequestBody ContestRequest contestRequest
            ) {
        try {
            return contestService.addContest(jwtService.extractUsername(token.replace("Bearer ", "")), contestRequest);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

}
