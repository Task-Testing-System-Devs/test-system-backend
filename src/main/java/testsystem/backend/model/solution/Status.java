package testsystem.backend.model.solution;

import jakarta.persistence.*;
import lombok.*;

/**
 * Class-model corresponding to "status" table in database.
 */
@Entity
@Table(name = "status")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Status {

    @Column(name = "status_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Integer id;

    @Column(name = "title")
    @Getter
    @Setter
    private String title;

}
