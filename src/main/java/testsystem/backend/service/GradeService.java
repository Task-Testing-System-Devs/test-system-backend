package testsystem.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import testsystem.backend.dto.UserShortInfo;
import testsystem.backend.dto.UserShortInfoPair;
import testsystem.backend.model.contest.Contest;
import testsystem.backend.model.contest.ContestConn;
import testsystem.backend.model.contest.Task;
import testsystem.backend.model.contest.TaskConn;
import testsystem.backend.model.education.Department;
import testsystem.backend.model.education.DepartmentConn;
import testsystem.backend.model.education.EdGroup;
import testsystem.backend.model.education.EdGroupConn;
import testsystem.backend.model.user.User;
import testsystem.backend.model.user.UserInfo;
import testsystem.backend.model.user.UserInfoForRatingDownload;
import testsystem.backend.repository.contest.ContestConnRepository;
import testsystem.backend.repository.contest.ContestRepository;
import testsystem.backend.repository.contest.TaskConnRepository;
import testsystem.backend.repository.contest.TaskRepository;
import testsystem.backend.repository.education.DepartmentConnRepository;
import testsystem.backend.repository.education.DepartmentRepository;
import testsystem.backend.repository.education.EdGroupConnRepository;
import testsystem.backend.repository.education.EdGroupRepository;
import testsystem.backend.repository.user.UserInfoRepository;
import testsystem.backend.repository.user.UserRepository;

import java.util.*;

/**
 * This service class handles user grade-related functionalities.
 */
@Service
public class GradeService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private DepartmentConnRepository departmentConnRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private EdGroupConnRepository edGroupConnRepository;
    @Autowired
    private EdGroupRepository edGroupRepository;
    @Autowired
    private ContestConnRepository contestConnRepository;
    @Autowired
    private ContestRepository contestRepository;
    @Autowired
    private TaskConnRepository taskConnRepository;
    @Autowired
    private TaskRepository taskRepository;

    /**
     * Retrieves a list of all users, sorted by the average grade they received in all the
     * contests they participated in, and then by the total number of contests they participated
     * in, in descending order.
     *
     * @return ResponseEntity object containing the sorted list of users and their grades in the response body.
     */
    public ResponseEntity<?> getGradesByMarks() {
        List<User> users = userRepository.findAll();
        users.sort(Comparator.comparing(this::getContestsAverageUserGrade).reversed());
        users.sort(Comparator.comparing(this::getContestsAmountOfUser).reversed());
        return ResponseEntity.ok(generateResponse(users));
    }

    /**
     * Retrieves a list of users sorted by their average contest grade and contests amount, and generates
     * a list of {@link UserInfoForRatingDownload} objects containing the user's information for downloading.
     *
     * @return List of {@link UserInfoForRatingDownload} objects sorted by average contest grade and contests amount.
     */
    public List<UserInfoForRatingDownload> getSortedByMarksUsersForDownload() {
        List<User> users = userRepository.findAll();
        users.sort(Comparator.comparing(this::getContestsAverageUserGrade).reversed());
        users.sort(Comparator.comparing(this::getContestsAmountOfUser).reversed());

        return generateUsersListForDownload(users);
    }

    /**
     * Generates a list of {@link UserInfoForRatingDownload} objects from a list of {@link User} objects,
     * sorted by the user's rating position.
     * The method retrieves the user's information from the corresponding repositories.
     *
     * @param users List of {@link User} objects to generate the download list from.
     * @return List of {@link UserInfoForRatingDownload} objects representing the users sorted by rating position.
     * @throws NoSuchElementException If any of the required data is missing from the corresponding repositories.
     */
    private List<UserInfoForRatingDownload> generateUsersListForDownload(List<User> users) {
        List<UserInfoForRatingDownload> userInfoForRatingDownloads = new ArrayList<>();
        for (int i = 0; i < users.size(); ++i) {
            User user = users.get(i);
            UserInfo userInfo = userInfoRepository.getUserInfoByUserId(user.getId()).orElseThrow();
            Optional<DepartmentConn> departmentConn = departmentConnRepository.findDepartmentConnByUserId(user.getId());
            Optional<Department> department = departmentRepository.findById(departmentConn.orElseThrow().getDepartmentId());
            Optional<EdGroupConn> edGroupConn = edGroupConnRepository.findEdGroupConnByDepartmentId(department.orElseThrow().getId());
            Optional<EdGroup> edGroup = edGroupRepository.findById(edGroupConn.orElseThrow().getEdGroupId());

            userInfoForRatingDownloads.add(UserInfoForRatingDownload.builder()
                    .ratingPosition(i + 1)
                    .lastName(userInfo.getLastName())
                    .firstName(userInfo.getFirstName())
                    .email(user.getEmail())
                    .department(department.get().getTitle())
                    .groupName(edGroup.orElseThrow().getTitle())
                    .build());
        }
        return userInfoForRatingDownloads;
    }

    /**
     * This method retrieves all users from the database and sorts them in descending order
     * based on the number of tasks they have solved, and generates a response with the sorted user list.
     *
     * @return ResponseEntity with the response body containing the sorted user list based on the number of solved tasks.
     */
    public ResponseEntity<?> getGradesByTasksAmount() {
        List<User> users = userRepository.findAll();
        users.sort(Comparator.comparing(this::getAllSolvedTasksAmount).reversed());
        return ResponseEntity.ok(generateResponse(users));
    }

    /**
     * Generates a list of users sorted by the amount of solved tasks and creates a {@link UserInfoForRatingDownload}
     * object for each user in the list, which contains user information required for rating download.
     *
     * @return List of UserInfoForRatingDownload objects sorted by the amount of solved tasks.
     */
    public List<UserInfoForRatingDownload> getSortedByTasksAmountUsersForDownload() {
        List<User> users = userRepository.findAll();
        users.sort(Comparator.comparing(this::getAllSolvedTasksAmount).reversed());

        return generateUsersListForDownload(users);
    }

    /**
     * Generates a list of {@link UserShortInfoPair} based on the given list of users.
     * For each user, it retrieves their information, department and educational group
     * to create a {@link UserShortInfoPair} with a {@link UserShortInfo} object containing
     * relevant information. If the user is a teacher, the department and educational group fields
     * will be set to "null".
     *
     * @param users List of users to generate response models for.
     * @return List of {@link UserShortInfoPair} with relevant user information.
     */
    private List<UserShortInfoPair> generateResponse(List<User> users) {
        List<UserShortInfoPair> responseModels = new ArrayList<>();

        for (var user : users) {
            Optional<UserInfo> userInfo = userInfoRepository.getUserInfoByUserId(user.getId());

            // If the user is a teacher, set the department and educational group fields to "null".
            if (Objects.equals(user.getRole(), "teacher")) {
                responseModels.add(new UserShortInfoPair(
                        user.getId(),
                        new UserShortInfo(
                                user.getEmail(), user.getRole(),
                                userInfo.orElseThrow().getFirstName(), userInfo.orElseThrow().getLastName(),
                                userInfo.orElseThrow().getMiddleName(), "null", "null"
                        ))
                );
                continue;
            }

            Optional<DepartmentConn> departmentConn = departmentConnRepository.findDepartmentConnByUserId(user.getId());
            Optional<Department> department = departmentRepository.findById(departmentConn.orElseThrow().getDepartmentId());
            Optional<EdGroupConn> edGroupConn = edGroupConnRepository.findEdGroupConnByDepartmentId(department.orElseThrow().getId());
            Optional<EdGroup> edGroup = edGroupRepository.findById(edGroupConn.orElseThrow().getEdGroupId());

            responseModels.add(new UserShortInfoPair(
                    user.getId(),
                    new UserShortInfo(
                            user.getEmail(), user.getRole(),
                            userInfo.orElseThrow().getFirstName(), userInfo.orElseThrow().getLastName(),
                            userInfo.orElseThrow().getMiddleName(), department.orElseThrow().getTitle(),
                            edGroup.orElseThrow().getTitle()
                    ))
            );
        }
        return responseModels;
    }

    /**
     * Returns the number of contests a user has participated in.
     *
     * @param user User whose contests are to be counted.
     * @return The number of contests participated in by the user.
     */
    private int getContestsAmountOfUser(User user) {
        List<ContestConn> contestsOfUser = contestConnRepository.findAllByUserId(user.getId());
        return contestsOfUser.size();
    }

    /**
     * Computes the average grade of all contests that the given user has participated in.
     *
     * @param user User whose contest grades will be averaged.
     * @return Average grade of the user's contest participation, or 0.0 if the user has not participated
     * in any contests.
     * @throws NoSuchElementException If any contest or task referenced by the user's contest participation
     * cannot be found in the database.
     */
    private Double getContestsAverageUserGrade(User user) {
        double totalForContests = 0.0;
        List<ContestConn> contestsOfUser = contestConnRepository.findAllByUserId(user.getId());

        for (ContestConn contestConn : contestsOfUser) {
            Optional<Contest> contest = contestRepository.findById(contestConn.getContestId());
            Double totalForContest = 0.0;
            List<TaskConn> taskConns = taskConnRepository.findAllByContestId(contest.orElseThrow().getId());

            for (TaskConn taskConn : taskConns) {
                Optional<Task> task = taskRepository.findById(taskConn.getTaskId());
                totalForContest += task.orElseThrow().getTaskGrade();
            }

            totalForContest /= taskConns.size();
            totalForContests += totalForContest;
        }

        totalForContests /= contestsOfUser.size();
        return totalForContests;
    }

    /**
     * Calculates the total number of solved tasks for a given user.
     *
     * @param user User to calculate the number of solved tasks.
     * @return Total number of solved tasks for the user.
     */
    private Integer getAllSolvedTasksAmount(User user) {
        int totalTasksAmount = 0;
        List<ContestConn> contestsOfUser = contestConnRepository.findAllByUserId(user.getId());

        for (ContestConn contestConn : contestsOfUser) {
            Optional<Contest> contest = contestRepository.findById(contestConn.getContestId());
            List<TaskConn> taskConns = taskConnRepository.findAllByContestId(contest.orElseThrow().getId());
            totalTasksAmount += taskConns.size();
        }
        
        return totalTasksAmount;
    }
}
