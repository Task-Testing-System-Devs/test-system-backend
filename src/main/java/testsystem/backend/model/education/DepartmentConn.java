package testsystem.backend.model.education;

import jakarta.persistence.*;
import lombok.*;

/**
 * Class-model corresponding to "departments_conn" table in database.
 */
@Entity
@Table(name = "departments_conn")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentConn {

    @Column(name = "departments_conn_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Integer id;

    @Column(name = "department_id")
    @Getter
    @Setter
    private Integer departmentId;

    @Column(name = "user_id")
    @Getter
    @Setter
    private Integer userId;

}
