package testsystem.backend.model.education;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ed_groups")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EdGroup {

    @Column(name = "ed_group_id")
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
