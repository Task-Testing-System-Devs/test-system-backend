package testsystem.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import testsystem.backend.dto.TeacherRegisterRequest;
import testsystem.backend.service.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/add-teacher")
    public ResponseEntity<?> addTeacher(
            @RequestHeader("Authorization") String data,
            @RequestBody TeacherRegisterRequest newTeacher
            ) {
        try {
            if (!data.equals("Basic YWRtaW46YWRtaW4=")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            return adminService.addTeacher(newTeacher);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
