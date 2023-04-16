package testsystem.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import testsystem.backend.dto.UserShortInfo;
import testsystem.backend.dto.UserShortInfoPair;
import testsystem.backend.model.contest.Contest;
import testsystem.backend.model.contest.ContestConn;
import testsystem.backend.model.contest.Task;
import testsystem.backend.model.contest.TaskConn;
import testsystem.backend.model.user.User;
import testsystem.backend.repository.contest.ContestConnRepository;
import testsystem.backend.repository.contest.ContestRepository;
import testsystem.backend.repository.contest.TaskConnRepository;
import testsystem.backend.repository.contest.TaskRepository;
import testsystem.backend.repository.user.UserRepository;

import java.util.*;

@Service
public class GradeService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContestConnRepository contestConnRepository;
    @Autowired
    private ContestRepository contestRepository;
    @Autowired
    private TaskConnRepository taskConnRepository;
    @Autowired
    private TaskRepository taskRepository;

    public ResponseEntity<?> getGradesByMarks() {
        List<User> users = userRepository.findAll();
        users.sort(Comparator.comparing(this::getContestsAverageUserGrade).reversed());
        users.sort(Comparator.comparing(this::getContestsAmountOfUser).reversed());
        return ResponseEntity.ok(generateResponse(users));
    }

    public ResponseEntity<?> getGradesByTasksAmount() {
        List<User> users = userRepository.findAll();
        users.sort(Comparator.comparing(this::getAllSolvedTasksAmount).reversed());
        return ResponseEntity.ok(generateResponse(users));
    }

    private List<UserShortInfoPair> generateResponse(List<User> users) {
        List<UserShortInfoPair> responseModels = new ArrayList<>();
        for (var user : users) {
            responseModels.add(new UserShortInfoPair(
                    user.getId(),
                    new UserShortInfo(user.getEmail(), user.getRole()))
            );
        }
        return responseModels;
    }

    private int getContestsAmountOfUser(User user) {
        List<ContestConn> contestsOfUser = contestConnRepository.findAllByUserId(user.getId());
        return contestsOfUser.size();
    }

    private Double getContestsAverageUserGrade(User user) {
        Double totalForContests = 0.0;
        List<ContestConn> contestsOfUser = contestConnRepository.findAllByUserId(user.getId());
        for (ContestConn contestConn : contestsOfUser) {
            Optional<Contest> contest = contestRepository.findById(contestConn.getContestId());
            Double totalForContest = 0.0;
            List<TaskConn> taskConns = taskConnRepository.findAllByContestId(contest.get().getId());
            for (TaskConn taskConn : taskConns) {
                Optional<Task> task = taskRepository.findById(taskConn.getTaskId());
                totalForContest += task.get().getTaskGrade();
            }
            totalForContest /= taskConns.size();
            totalForContests += totalForContest;
        }
        totalForContests /= contestsOfUser.size();
        return totalForContests;
    }

    private Integer getAllSolvedTasksAmount(User user) {
        Integer totalTasksAmount = 0;
        List<ContestConn> contestsOfUser = contestConnRepository.findAllByUserId(user.getId());
        for (ContestConn contestConn : contestsOfUser) {
            Optional<Contest> contest = contestRepository.findById(contestConn.getContestId());
            List<TaskConn> taskConns = taskConnRepository.findAllByContestId(contest.get().getId());
            totalTasksAmount += taskConns.size();
        }
        return totalTasksAmount;
    }

}
