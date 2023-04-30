package testsystem.backend.model.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_solutions")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSolution {

    @Column(name = "user_solutions_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Integer id;

    @Column(name = "user_id")
    @Getter
    @Setter
    private Integer userId;

    @Column(name = "solution_id")
    @Getter
    @Setter
    private Integer solutionId;

}
