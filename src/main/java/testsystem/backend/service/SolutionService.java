package testsystem.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import testsystem.backend.dto.SolutionDTOObject;
import testsystem.backend.model.contest.*;
import testsystem.backend.model.solution.Language;
import testsystem.backend.model.solution.Solution;
import testsystem.backend.model.solution.Status;
import testsystem.backend.model.user.User;
import testsystem.backend.model.user.UserSolution;
import testsystem.backend.repository.contest.*;
import testsystem.backend.repository.solution.LanguageRepository;
import testsystem.backend.repository.solution.SolutionRepository;
import testsystem.backend.repository.solution.StatusRepository;
import testsystem.backend.repository.user.UserRepository;
import testsystem.backend.repository.user.UserSolutionsRepository;

import java.util.*;

/**
 * This class provides the implementation for solution service.
 */
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
    @Autowired
    private ContestConnRepository contestConnRepository;

    /**
     * Retrieves a list of solutions made by the user with the specified email address.
     *
     * @param email Email address of the user to retrieve solutions for.
     * @return ResponseEntity object containing either the list of SolutionDTOObject responses or an error message
     * if the user has no solutions.
     */
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
            Optional<Solution> solution = solutionRepository.findById(userSolutionEntity.orElseThrow(
                    () -> new NoSuchElementException("User solution entity has id that doesn't exist in database")
            ).getSolutionId());

            Optional<Language> language = languageRepository.findById(solution.orElseThrow(
                    () -> new NoSuchElementException("Solution for language was not found")
            ).getLanguageId());
            Optional<Status> status = statusRepository.findById(solution.orElseThrow(
                    () -> new NoSuchElementException("Solution for status was not found")
            ).getStatusId());

            Optional<UniqueContestTask> uniqueContestTask = uniqueContestTaskRepository.findById(solution.orElseThrow(
                    () -> new NoSuchElementException("Solution for unique contest task connection was not found")
            ).getUniqueTaskId());
            Optional<Contest> contest = contestRepository.findById(uniqueContestTask.orElseThrow(
                    () -> new NoSuchElementException("Solution for contest was not found")
            ).getContestId());
            Optional<Task> task = taskRepository.findById(uniqueContestTask.orElseThrow(
                    () -> new NoSuchElementException("Unique contest task for task was not found")
            ).getTaskId());

            SolutionDTOObject solutionResponse = SolutionDTOObject.builder()
                    .id(solution.orElseThrow(
                            () -> new NoSuchElementException("Solution for id while DTO creation was not found")
                    ).getId())
                    .code(solution.orElseThrow(
                            () -> new NoSuchElementException("Solution for code while DTO creation was not found")
                    ).getCode())
                    .error_test(solution.orElseThrow(
                            () -> new NoSuchElementException("Solution for error test while DTO creation was not found")
                    ).getErrorTest())
                    .language(language.orElseThrow(
                            () -> new NoSuchElementException("Solution for language while DTO creation was not found")
                    ).getTitle())
                    .status(status.orElseThrow(
                            () -> new NoSuchElementException("Solution for status while DTO creation was not found")
                    ).getTitle())
                    .used_time(solution.orElseThrow(
                            () -> new NoSuchElementException("Solution for used time while DTO creation was not found")
                    ).getUsedTime())
                    .used_memory(solution.orElseThrow(
                            () -> new NoSuchElementException("Solution for used memory while DTO creation was not found")
                    ).getUsedMemory())
                    .contest_name(contest.orElseThrow(
                            () -> new NoSuchElementException("Solution for contest name while DTO creation was not found")
                    ).getTitle())
                    .task_name(task.orElseThrow(
                            () -> new NoSuchElementException("Solution for task name while DTO creation was not found")
                    ).getTitle())
                    .build();

            solutions.add(solutionResponse);
        }

        return ResponseEntity.ok(solutions);
    }

    /**
     * Adds a new solution to the database for the specified user.
     *
     * @param email Email of the user to add a solution for.
     * @param solutionDTOObject Solution DTO object containing the information about the solution.
     * @return ResponseEntity object indicating whether the operation was successful or not.
     */
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
        List<TaskConn> contestTasks = taskConnRepository.findAllByContestId(contest.orElseThrow(
                () -> new NoSuchElementException("Contest for contest tasks were not found")
        ).getId());
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
                .contestId(contest.orElseThrow(
                        () -> new NoSuchElementException("Contest for contest id while UniqueContestTask build was not found")
                ).getId())
                .taskId(task.orElseThrow(
                        () -> new NoSuchElementException("Task for task id while UniqueContestTask build was not found")
                ).getId())
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
                .userId(user.orElseThrow(
                        () -> new NoSuchElementException("User for user id while DTO creation was not found")
                ).getId())
                .solutionId(solution.getId())
                .build();
        userSolutionsRepository.save(userSolutions);

        if (!contestConnRepository.existsByUserId(user.get().getId())) {
            ContestConn contestConn = ContestConn.builder()
                    .userId(user.get().getId())
                    .contestId(contest.get().getId())
                    .build();
            contestConnRepository.save(contestConn);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("New solution was successfully added");
    }

    /**
     * Returns all solutions if request is from teacher for all tasks and contests in the system.
     *
     * @param email Email of the teacher who wants to get all solutions.
     * @return ResponseEntity object indicating whether the operation was successful or not.
     */
    public ResponseEntity<?> getAllSolutions(String email) {
        // Extract user main info.
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("No user with email: <" + email + "> found");
        }

        if (!Objects.equals(user.get().getRole(), "teacher")) {
            return ResponseEntity.badRequest().body("User with email: <" + email + "> is not a teacher and has role: <" + user.get().getRole() + ">");
        }

        List<Solution> solutions = solutionRepository.findAll();
        if (solutions.isEmpty()) {
            return ResponseEntity.badRequest().body("There are no solutions in system yet");
        }

        List<SolutionDTOObject> solutionsResponse = new ArrayList<>();
        for (var solution : solutions) {
            Optional<Language> language = languageRepository.findById(solution.getLanguageId());
            Optional<Status> status = statusRepository.findById(solution.getStatusId());
            solutionsResponse.add(
                    SolutionDTOObject.builder()
                            .id(solution.getId())
                            .task_name("null")
                            .contest_name("null")
                            .code(solution.getCode())
                            .error_test(solution.getErrorTest())
                            .language(language.orElseThrow(
                                    () -> new NoSuchElementException("Language is not found")
                            ).getTitle())
                            .status(status.orElseThrow(
                                    () -> new NoSuchElementException("Status is not found")
                            ).getTitle())
                            .used_time(solution.getUsedTime())
                            .used_memory(solution.getUsedMemory())
                            .build()
            );
        }

        return ResponseEntity.ok(solutionsResponse);
    }
}
