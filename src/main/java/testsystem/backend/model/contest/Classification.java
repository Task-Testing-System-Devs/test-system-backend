package testsystem.backend.model.contest;

import jakarta.persistence.*;
import lombok.*;

/**
 * Class-model corresponding to "classifications" table in database.
 */
@Entity
@Table(name = "classifications")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Classification {

    @Column(name = "classification_id")
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
