package testsystem.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import testsystem.backend.dto.TeacherRegisterRequest;
import testsystem.backend.service.AdminService;

/**
 * Controller class for Admin-related operations.
 */
@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * Adds a new teacher to the system.
     *
     * @param data Authorization header containing the credentials of user making request.
     * @param newTeacher DTO containing the details of new teacher to be added to the system.
     * @return HTTP response indicating the outcome of the request.
     */
    @CrossOrigin(origins = "*")
    @PostMapping("/add-teacher")
    public ResponseEntity<?> addTeacher(
            @RequestHeader("Authorization") String data,
            @RequestBody TeacherRegisterRequest newTeacher
            ) {
        try {
            if (!data.equals("Basic YWRtaW46YWRtaW4=")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            // Forward the request in successful case of auth to the AdminService for functionality implementation.
            return adminService.addTeacher(newTeacher);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
