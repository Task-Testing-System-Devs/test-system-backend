package testsystem.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import testsystem.backend.model.user.UserInfoForRatingDownload;
import testsystem.backend.service.GradeService;
import testsystem.backend.service.JwtService;
import testsystem.backend.utilities.FileService;

import java.util.List;

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

    @GetMapping("/by-marks/download-csv")
    public ResponseEntity<?> downloadCSVRatingByMarks() {
        try {
            List<UserInfoForRatingDownload> userInfoForRatingDownloadList = gradeService.getSortedByMarksUsersForDownload();
            FileService fileService = new FileService();
            fileService.convertToCSV(userInfoForRatingDownloadList.get(0).toString(), userInfoForRatingDownloadList, "rating_marks.csv");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "rating_marks.csv");
            FileSystemResource file = new FileSystemResource("rating_marks.csv");
            return new ResponseEntity<>(file, headers, HttpStatus.OK);
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

    @GetMapping("/by-tasks-amount/download-csv")
    public ResponseEntity<?> downloadCSVRatingByTasksAmount() {
        try {
            List<UserInfoForRatingDownload> userInfoForRatingDownloadList = gradeService.getSortedByTasksAmountUsersForDownload();
            FileService fileService = new FileService();
            fileService.convertToCSV(userInfoForRatingDownloadList.get(0).toString(), userInfoForRatingDownloadList, "rating_tasks_amount.csv");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "rating_tasks_amount.csv");
            FileSystemResource file = new FileSystemResource("rating_tasks_amount.csv");

            return new ResponseEntity<>(file, headers, HttpStatus.OK);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }


}
