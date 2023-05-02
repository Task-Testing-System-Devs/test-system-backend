package testsystem.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import testsystem.backend.dto.ContestRequest;
import testsystem.backend.model.contest.Classification;
import testsystem.backend.model.contest.Contest;
import testsystem.backend.model.contest.Task;
import testsystem.backend.model.contest.TaskConn;
import testsystem.backend.model.user.User;
import testsystem.backend.repository.contest.ClassificationRepository;
import testsystem.backend.repository.contest.ContestRepository;
import testsystem.backend.repository.contest.TaskConnRepository;
import testsystem.backend.repository.contest.TaskRepository;
import testsystem.backend.repository.user.UserRepository;

import java.util.Objects;
import java.util.Optional;


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

    @Transactional
    public ResponseEntity<?> addContest(String email, ContestRequest contestRequest) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("The user with such email does not exist");
        }
        if (!Objects.equals(user.get().getRole(), "teacher")) {
            return ResponseEntity.badRequest().body("The user with role: <" + user.get().getRole() + "> is not permitted to add contest");
        }

        Contest contest = Contest.builder()
                .title(contestRequest.getTitle())
                .startTime(contestRequest.getStart_time().toLocalDateTime())
                .finishTime(contestRequest.getFinish_time().toLocalDateTime())
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
}
