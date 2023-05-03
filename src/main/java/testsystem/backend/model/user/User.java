package testsystem.backend.model.user;

import jakarta.persistence.*;
import lombok.*;

/**
 * Class-model corresponding to "users" table in database.
 */
@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Column(name = "user_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Integer id;

    @Column(name = "email")
    @Getter
    @Setter
    private String email;

    @Column(name = "role")
    @Getter
    @Setter
    private String role;

    @Column(name = "password")
    @Getter
    @Setter
    private String password;

}
