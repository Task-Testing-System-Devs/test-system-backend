package testsystem.backend.model.contest;

import jakarta.persistence.*;
import lombok.*;

/**
 * Class-model corresponding to "tasks_of_contest" table in database.
 */
@Entity
@Table(name = "tasks_of_contest")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskConn {

    @Column(name = "tasks_of_contest_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Integer id;

    @Column(name = "contest_id")
    @Getter
    @Setter
    private Integer contestId;

    @Column(name = "task_id")
    @Getter
    @Setter
    private Integer taskId;

}
