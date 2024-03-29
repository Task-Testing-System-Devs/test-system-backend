package testsystem.backend.model.solution;

import jakarta.persistence.*;
import lombok.*;

/**
 * Class-model corresponding to "languages" table in database.
 */
@Entity
@Table(name = "languages")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Language {

    @Column(name = "language_id")
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
