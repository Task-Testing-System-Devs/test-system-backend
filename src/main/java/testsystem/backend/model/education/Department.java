package testsystem.backend.model.education;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "departments")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {

    @Column(name = "department_id")
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
