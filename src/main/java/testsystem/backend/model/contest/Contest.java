package testsystem.backend.model.contest;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Class-model corresponding to "contests" table in database.
 */
@Entity
@Table(name = "contests")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contest {

    @Column(name = "contest_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Integer id;

    @Column(name = "title")
    @Getter
    @Setter
    private String title;

    @Column(name = "start_time")
    @Getter
    @Setter
    private LocalDateTime startTime;

    @Column(name = "finish_time")
    @Getter
    @Setter
    private LocalDateTime finishTime;

    @Column(name = "is_resolvable")
    @Getter
    @Setter
    private boolean isResolvable;

    @Column(name = "is_mark_rated")
    @Getter
    @Setter
    private boolean isMarkRated;

    @Column(name = "is_task_rated")
    @Getter
    @Setter
    private boolean isTaskRated;

}
