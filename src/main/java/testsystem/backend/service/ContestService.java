package testsystem.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import testsystem.backend.dto.ContestDTOObject;
import testsystem.backend.dto.TaskDTOObject;
import testsystem.backend.model.contest.*;
import testsystem.backend.model.user.User;
import testsystem.backend.repository.contest.*;
import testsystem.backend.repository.user.UserRepository;

import java.util.*;

/**
 * This service class handles contest-related functionalities.
 */
@Service
public class ContestService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContestRepository contestRepository;
    @Autowired
    private ClassificationRepository classificationRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskConnRepository taskConnRepository;
    @Autowired
    private ContestConnRepository contestConnRepository;

    /**
     * Adds a new contest to the system.
     *
     @param email The email of user adding the contest.
     @param contestRequest The ContestRequest object containing details of new contest.
     @return ResponseEntity a response object indicating the success or failure of the request.
     */
    @Transactional
    public ResponseEntity<?> addContest(String email, ContestDTOObject contestRequest) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("The user with such email does not exist");
        }
        if (!Objects.equals(user.get().getRole(), "teacher")) {
            return ResponseEntity.badRequest()
                    .body("The user with role: <" + user.get().getRole() + "> is not permitted to add contest");
        }

        Contest contest = Contest.builder()
                .ejudgeId(contestRequest.getEjudge_id())
                .title(contestRequest.getTitle())
                .startTime(contestRequest.getStart_time())
                .finishTime(contestRequest.getFinish_time())
                .isResolvable(true)
                .isMarkRated(true)
                .isTaskRated(true)
                .build();
        contestRepository.save(contest);

        for (var requestTask : contestRequest.getTasks()) {

            Classification classification = Classification.builder()
                    .title(requestTask.getClassification_title())
                    .build();
            classificationRepository.save(classification);

            Task task = Task.builder()
                    .title(requestTask.getTitle())
                    .description(requestTask.getDescription())
                    .memoryLimit(requestTask.getMemory_limit())
                    .timeLimit(requestTask.getTime_limit())
                    .attemptsAmount(requestTask.getAttempts_amount())
                    .classificationId(classification.getId())
                    .difficulty(1.0)
                    .is_private(false)
                    .grade(10.0)
                    .build();
            taskRepository.save(task);

            TaskConn taskConn = TaskConn.builder()
                    .taskId(task.getId())
                    .contestId(contest.getId())
                    .build();
            taskConnRepository.save(taskConn);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("New contest was successfully added to system");
    }

    /**
     * Get all contests of specified user.
     *
     * @param email Email address of the user to retrieve contests for.
     * @return ResponseEntity containing a list of ContestDTOObjects or an error message.
     */
    public ResponseEntity<?> getAllUserContests(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("The user with such email does not exist");
        }

        List<ContestConn> contestConns = contestConnRepository.findAllByUserId(user.get().getId());
        List<ContestDTOObject> contestsResponse = new ArrayList<>();
        for (var contestConn : contestConns) {
            Optional<Contest> contest = contestRepository.findById(contestConn.getContestId());
            if (contest.isEmpty()) {
                return ResponseEntity.badRequest().body("Contest is not found");
            }
            List<TaskConn> taskConns = taskConnRepository.findAllByContestId(contest.orElseThrow(
                    () -> new NoSuchElementException("Contest is not found")).getId());
            List<TaskDTOObject> taskDTOObjects = new ArrayList<>();
            for (var taskConn : taskConns) {
                Optional<Task> task = taskRepository.findById(taskConn.getTaskId());
                if (task.isEmpty()) {
                    return ResponseEntity.badRequest().body("Task of contest: <" + contest.get().getTitle() + "> is not found");
                }
                Optional<Classification> classification = classificationRepository.findById(task.get().getClassificationId());
                if (classification.isEmpty()) {
                    return ResponseEntity.badRequest().body("Classification of task: <" + task.get().getTitle() + "> is not found");
                }
                taskDTOObjects.add(
                        TaskDTOObject.builder()
                                .title(task.get().getTitle())
                                .attempts_amount(task.get().getAttemptsAmount())
                                .description(task.get().getDescription())
                                .memory_limit(task.get().getMemoryLimit())
                                .time_limit(task.get().getTimeLimit())
                                .classification_title(classification.get().getTitle())
                                .build()
                );
            }
            contestsResponse.add(
                    ContestDTOObject.builder()
                            .ejudge_id(contest.get().getEjudgeId())
                            .title(contest.get().getTitle())
                            .start_time(contest.get().getStartTime())
                            .finish_time(contest.get().getFinishTime())
                            .tasks(taskDTOObjects)
                            .build()
            );
        }

        return ResponseEntity.ok(contestsResponse);
    }

    /**
     * Retrieves all contests.
     *
     * @return ResponseEntity containing a list of ContestDTOObjects or an error message.
     */
    public ResponseEntity<?> getAllContests() {
        List<ContestDTOObject> contestsResponse = new ArrayList<>();
        List<Contest> contests = contestRepository.findAll();
        for (var contest : contests) {
            List<TaskConn> taskConns = taskConnRepository.findAllByContestId(contest.getId());
            List<TaskDTOObject> taskDTOObjects = new ArrayList<>();
            for (var taskConn : taskConns) {
                Optional<Task> task = taskRepository.findById(taskConn.getTaskId());
                if (task.isEmpty()) {
                    return ResponseEntity.badRequest().body("Task of contest: <" + contest.getTitle() + "> is not found");
                }
                Optional<Classification> classification = classificationRepository.findById(task.get().getClassificationId());
                if (classification.isEmpty()) {
                    return ResponseEntity.badRequest().body("Classification of task: <" + task.get().getTitle() + "> is not found");
                }
                taskDTOObjects.add(
                        TaskDTOObject.builder()
                                .title(task.get().getTitle())
                                .attempts_amount(task.get().getAttemptsAmount())
                                .description(task.get().getDescription())
                                .memory_limit(task.get().getMemoryLimit())
                                .time_limit(task.get().getTimeLimit())
                                .classification_title(classification.get().getTitle())
                                .build()
                );
            }
            contestsResponse.add(
                    ContestDTOObject.builder()
                            .ejudge_id(contest.getEjudgeId())
                            .title(contest.getTitle())
                            .start_time(contest.getStartTime())
                            .finish_time(contest.getFinishTime())
                            .tasks(taskDTOObjects)
                            .build()
            );
        }

        return ResponseEntity.ok(contestsResponse);
    }
}
