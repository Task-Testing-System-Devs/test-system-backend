package testsystem.backend.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTOObject {

    @NonNull
    private String title;

    @NonNull
    private String description;

    @NonNull
    private Double memory_limit;

    @NonNull
    private Double time_limit;

    @NonNull
    private Integer attempts_amount;

    @NonNull
    private String classification_title;

}