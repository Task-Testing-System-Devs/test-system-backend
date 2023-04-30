package testsystem.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import testsystem.backend.dto.SolutionDTOObject;
import testsystem.backend.model.contest.Contest;
import testsystem.backend.model.contest.Task;
import testsystem.backend.model.contest.TaskConn;
import testsystem.backend.model.contest.UniqueContestTask;
import testsystem.backend.model.solution.Language;
import testsystem.backend.model.solution.Solution;
import testsystem.backend.model.solution.Status;
import testsystem.backend.model.user.User;
import testsystem.backend.model.user.UserSolution;
import testsystem.backend.repository.contest.ContestRepository;
import testsystem.backend.repository.contest.TaskConnRepository;
import testsystem.backend.repository.contest.TaskRepository;
import testsystem.backend.repository.contest.UniqueContestTaskRepository;
import testsystem.backend.repository.solution.LanguageRepository;
import testsystem.backend.repository.solution.SolutionRepository;
import testsystem.backend.repository.solution.StatusRepository;
import testsystem.backend.repository.user.UserRepository;
import testsystem.backend.repository.user.UserSolutionsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SolutionService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserSolutionsRepository userSolutionsRepository;
    @Autowired
    private SolutionRepository solutionRepository;
    @Autowired
    private LanguageRepository languageRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private UniqueContestTaskRepository uniqueContestTaskRepository;
    @Autowired
    private ContestRepository contestRepository;
    @Autowired
    private TaskConnRepository taskConnRepository;
    @Autowired
    private TaskRepository taskRepository;


    public ResponseEntity<?> getUserSolutions(String email) {
        // Extract user main info.
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("No user with email: <" + email + "> found");
        }

        List<Optional<UserSolution>> userSolutionsEntities = userSolutionsRepository.findAllByUserId(user.get().getId());
        if (userSolutionsEntities.isEmpty()) {
            return ResponseEntity.badRequest().body("User with email: <" + email + "> has no solutions");
        }

        List<SolutionDTOObject> solutions = new ArrayList<>();
        for (var userSolutionEntity : userSolutionsEntities) {
            Optional<Solution> solution = solutionRepository.findById(userSolutionEntity.orElseThrow().getSolutionId());

            Optional<Language> language = languageRepository.findById(solution.orElseThrow().getLanguageId());
            Optional<Status> status = statusRepository.findById(solution.orElseThrow().getStatusId());

            Optional<UniqueContestTask> uniqueContestTask = uniqueContestTaskRepository.findById(solution.orElseThrow().getUniqueTaskId());
            Optional<Contest> contest = contestRepository.findById(uniqueContestTask.orElseThrow().getContestId());
            Optional<Task> task = taskRepository.findById(uniqueContestTask.orElseThrow().getTaskId());

            SolutionDTOObject solutionResponse = SolutionDTOObject.builder()
                    .code(solution.orElseThrow().getCode())
                    .error_test(solution.orElseThrow().getErrorTest())
                    .language(language.orElseThrow().getTitle())
                    .status(status.orElseThrow().getTitle())
                    .used_time(solution.orElseThrow().getUsedTime())
                    .used_memory(solution.orElseThrow().getUsedMemory())
                    .contest_name(contest.orElseThrow().getTitle())
                    .task_name(task.orElseThrow().getTitle())
                    .build();

            solutions.add(solutionResponse);
        }

        return ResponseEntity.ok(solutions);
    }

    @Transactional
    public ResponseEntity<?> addUserSolution(String email, SolutionDTOObject solutionDTOObject) {
        // Extract user main info.
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("No user with email: <" + email + "> found");
        }

        Language language = Language.builder()
                .title(solutionDTOObject.getLanguage())
                .build();
        languageRepository.save(language);

        Status status = Status.builder()
                .title(solutionDTOObject.getStatus())
                .build();
        statusRepository.save(status);

        Optional<Contest> contest = contestRepository.findByTitle(solutionDTOObject.getContest_name());
        List<TaskConn> contestTasks = taskConnRepository.findAllByContestId(contest.orElseThrow().getId());
        Optional<Task> task = Optional.empty();
        for (var contestTask : contestTasks) {
            Optional<Task> taskOptional = taskRepository.findById(contestTask.getTaskId());
            if (taskOptional.isPresent()) {
                if (Objects.equals(taskOptional.get().getTitle(), solutionDTOObject.getTask_name())) {
                    task = taskOptional;
                    break;
                }
            }
        }

        UniqueContestTask uniqueContestTask = UniqueContestTask.builder()
                .weight(0.0)
                .contestId(contest.orElseThrow().getId())
                .taskId(task.orElseThrow().getId())
                .build();
        uniqueContestTaskRepository.save(uniqueContestTask);

        Solution solution = Solution.builder()
                .uniqueTaskId(uniqueContestTask.getId())
                .code(solutionDTOObject.getCode())
                .errorTest(solutionDTOObject.getError_test())
                .statusId(status.getId())
                .languageId(language.getId())
                .usedMemory(solutionDTOObject.getUsed_memory())
                .usedTime(solutionDTOObject.getUsed_time())
                .build();
        solutionRepository.save(solution);

        UserSolution userSolutions = UserSolution.builder()
                .userId(user.orElseThrow().getId())
                .solutionId(solution.getId())
                .build();
        userSolutionsRepository.save(userSolutions);

        return ResponseEntity.status(HttpStatus.CREATED).body("New solution was successfully added");
    }
}
