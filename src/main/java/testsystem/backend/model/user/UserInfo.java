package testsystem.backend.model.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users_info")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfo {

    @Column(name = "user_info_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Integer id;

    @Column(name = "user_id")
    @Getter
    @Setter
    private Integer userId;

    @Column(name = "first_name")
    @Getter
    @Setter
    private String firstName;

    @Column(name = "last_name")
    @Getter
    @Setter
    private String lastName;

    @Column(name = "middle_name", nullable = true)
    @Getter
    @Setter
    private String middleName;
}
