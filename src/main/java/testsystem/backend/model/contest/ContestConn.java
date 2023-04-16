package testsystem.backend.model.contest;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users_on_contest")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContestConn {

    @Column(name = "users_on_contest_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Integer id;

    @Column(name = "contest_id")
    @Getter
    @Setter
    private Integer contestId;

    @Column(name = "user_id")
    @Getter
    @Setter
    private Integer userId;

}
