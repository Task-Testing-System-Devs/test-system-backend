package testsystem.backend.model.solution;

import jakarta.persistence.*;
import lombok.*;

/**
 * Class-model corresponding to "solutions" table in database.
 */
@Entity
@Table(name = "solutions")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Solution {

    @Column(name = "solution_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Integer id;

    @Column(name = "unique_task_id")
    @Getter
    @Setter
    private Integer uniqueTaskId;

    @Column(name = "code")
    @Getter
    @Setter
    private String code;

    @Column(name = "language_id")
    @Getter
    @Setter
    private Integer languageId;

    @Column(name = "status_id")
    @Getter
    @Setter
    private Integer statusId;

    @Column(name = "used_time")
    @Getter
    @Setter
    private Double usedTime;

    @Column(name = "used_memory")
    @Getter
    @Setter
    private Double usedMemory;

    @Column(name = "error_test")
    @Getter
    @Setter
    private String errorTest;

}
