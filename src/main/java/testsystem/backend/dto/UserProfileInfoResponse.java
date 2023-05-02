package testsystem.backend.dto;

import jakarta.annotation.Nullable;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileInfoResponse {

    @NonNull
    private Integer id;

    @NonNull
    private String first_name;

    @NonNull
    private String last_name;

    @Nullable
    private String middle_name;

    @NonNull
    private String email;

    private String department;

    private String group;
}
