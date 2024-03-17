package testsystem.backend.dto;

import lombok.*;

/**
 * DTO class with many fields for sending request adding solution or retrieving data and sending response.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolutionDTOObject {

    @NonNull
    private Integer id;

    @NonNull
    private String code;

    @NonNull
    private Integer language;

    @NonNull
    private String status;

    @NonNull
    private Double used_time;

    @NonNull
    private Double used_memory;

    @NonNull
    private String error_test;

    @NonNull
    private Integer contest_id;

    @NonNull
    private Integer task_id;

}
