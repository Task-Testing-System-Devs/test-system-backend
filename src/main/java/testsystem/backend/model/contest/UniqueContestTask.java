package testsystem.backend.model.contest;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "unique_contest_tasks")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UniqueContestTask {

    @Column(name = "unique_task_id")
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

    @Column(name = "weight")
    @Getter
    @Setter
    private Double weight;

}
