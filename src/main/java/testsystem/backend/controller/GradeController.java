package testsystem.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import testsystem.backend.model.user.UserInfoForRatingDownload;
import testsystem.backend.service.GradeService;
import testsystem.backend.utilities.FileService;

import java.util.List;

/**
 * Controller class handling requests related to grades.
 */
@RestController
@RequestMapping("/api/grade")
@CrossOrigin(origins = "*")
public class GradeController {

    // Constants for file names.
    private static final String RATING_MARKS_NAME = "rating_marks.csv";
    private static final String RATING_TASKS_AMOUNT_NAME = "rating_tasks_amount.csv";

    // FileService instance for file handling.
    private final FileService fileService = new FileService();

    @Autowired
    private GradeService gradeService;

    /**
     * Returns ResponseEntity with grades sorted by marks.
     *
     * @return ResponseEntity with grades sorted by marks, or a bad request if an exception occurs.
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/by-marks")
    public ResponseEntity<?> getGradesByMarks() {
        try {
            return gradeService.getGradesByMarks();
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    /**
     * Returns ResponseEntity with grades sorted by tasks amount.
     *
     * @return ResponseEntity with grades sorted by tasks amount, or a bad request if an exception occurs.
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/by-tasks-amount")
    public ResponseEntity<?> getGradesByTasksAmount() {
        try {
            return gradeService.getGradesByTasksAmount();
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    /**
     * Downloads a CSV file with grades sorted by marks.
     *
     * @return ResponseEntity with the CSV file as a FileSystemResource, or a bad request if an exception occurs.
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/by-marks/download-csv")
    public ResponseEntity<?> downloadCSVRatingByMarks() {
        try {
            // Get the list of users with grades sorted by marks and convert it to CSV.
            List<UserInfoForRatingDownload> userInfoForRatingDownloadList = gradeService.getSortedByMarksUsersForDownload();

            fileService.convertToCSV(
                    userInfoForRatingDownloadList.get(0).toString(),
                    userInfoForRatingDownloadList, RATING_MARKS_NAME
            );

            // Set the headers for the ResponseEntity with the CSV file.
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", RATING_MARKS_NAME);

            // Create the FileSystemResource with the CSV file and return the ResponseEntity.
            FileSystemResource file = new FileSystemResource(RATING_MARKS_NAME);
            return new ResponseEntity<>(file, headers, HttpStatus.OK);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    /**
     * Downloads a CSV file with grades sorted by tasks amount.
     *
     * @return ResponseEntity with the CSV file as a FileSystemResource, or a bad request if an exception occurs.
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/by-tasks-amount/download-csv")
    public ResponseEntity<?> downloadCSVRatingByTasksAmount() {
        try {
            // Get the list of users with grades sorted by tasks amount and convert it to CSV.
            List<UserInfoForRatingDownload> userInfoForRatingDownloadList = gradeService.getSortedByTasksAmountUsersForDownload();

            fileService.convertToCSV(
                    userInfoForRatingDownloadList.get(0).toString(),
                    userInfoForRatingDownloadList, RATING_TASKS_AMOUNT_NAME
            );

            // Set the headers for the ResponseEntity with the CSV file.
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", RATING_TASKS_AMOUNT_NAME);

            // Create the FileSystemResource with the CSV file and return the ResponseEntity.
            FileSystemResource file = new FileSystemResource(RATING_TASKS_AMOUNT_NAME);
            return new ResponseEntity<>(file, headers, HttpStatus.OK);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
