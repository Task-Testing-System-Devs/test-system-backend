package testsystem.backend.model.education;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ed_groups_conn")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EdGroupConn {

    @Column(name = "ed_groups_conn_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Integer id;

    @Column(name = "ed_group_id")
    @Getter
    @Setter
    private Integer edGroupId;

    @Column(name = "department_id")
    @Getter
    @Setter
    private Integer departmentId;
}
