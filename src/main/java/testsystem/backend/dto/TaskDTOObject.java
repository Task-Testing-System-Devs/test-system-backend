package testsystem.backend.dto;

import lombok.*;

/**
 * DTO class with many fields with task info for sending request or response.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTOObject {
    private Integer id;

    @NonNull
    private String title;

    @NonNull
    private Integer ejudge_id;

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
