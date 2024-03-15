package testsystem.backend.model.contest;

import jakarta.persistence.*;
import lombok.*;

/**
 * Class-model corresponding to "tasks" table in database.
 */
@Entity
@Table(name = "tasks")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Column(name = "task_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Integer id;

    @Column(name = "ejudge_id")
    @Getter
    @Setter
    private Integer ejudgeId;

    @Column(name = "title")
    @Getter
    @Setter
    private String title;

    @Column(name = "description")
    @Getter
    @Setter
    private String description;

    @Column(name = "memory_limit")
    @Getter
    @Setter
    private Double memoryLimit;

    @Column(name = "time_limit")
    @Getter
    @Setter
    private Double timeLimit;

    @Column(name = "attempts_amount")
    @Getter
    @Setter
    private Integer attemptsAmount;

    @Column(name = "classification_id")
    @Getter
    @Setter
    private Integer classificationId;

    @Column(name = "difficulty")
    @Getter
    @Setter
    private Double difficulty;

    @Column(name = "is_private")
    @Getter
    @Setter
    private Boolean is_private;

    @Column(name = "grade")
    @Getter
    @Setter
    private Double grade;

    /**
     * Returns grade got for task.
     *
     * @return Grade counted by multiplication of grade and task difficulty.
     */
    public Double getTaskGrade() {
        return grade * difficulty;
    }

}
