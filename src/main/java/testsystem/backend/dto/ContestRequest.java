package testsystem.backend.dto;

import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContestRequest {

    @NonNull
    private String title;

    @NonNull
    private Timestamp start_time;

    @NonNull
    private Timestamp finish_time;

    @NonNull
    private List<TaskDTOObject> tasks;

}
