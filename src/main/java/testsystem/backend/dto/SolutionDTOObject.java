package testsystem.backend.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolutionDTOObject {

    @NonNull
    private String code;

    @NonNull
    private String language;

    @NonNull
    private String status;

    @NonNull
    private Double used_time;

    @NonNull
    private Double used_memory;

    @NonNull
    private String error_test;

    @NonNull
    private String contest_name;

    @NonNull
    private String task_name;

}