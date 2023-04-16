package testsystem.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import testsystem.backend.service.GradeService;
import testsystem.backend.service.JwtService;

@RestController
@RequestMapping("/api/grade")
public class GradeController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private GradeService gradeService;

    @GetMapping("/by-marks")
    public ResponseEntity<?> getGradesByMarks() {
        try {
            return gradeService.getGradesByMarks();
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @GetMapping("/by-tasks-amount")
    public ResponseEntity<?> getGradesByTasksAmount() {
        try {
            return gradeService.getGradesByTasksAmount();
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

}
