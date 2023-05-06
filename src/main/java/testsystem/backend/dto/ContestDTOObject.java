package testsystem.backend.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO class with fields title, start_time, finish_time and tasks for contest adding request.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContestDTOObject {

    @NonNull
    private String title;

    @NonNull
    private LocalDateTime start_time;

    @NonNull
    private LocalDateTime finish_time;

    @NonNull
    private List<TaskDTOObject> tasks;

}
