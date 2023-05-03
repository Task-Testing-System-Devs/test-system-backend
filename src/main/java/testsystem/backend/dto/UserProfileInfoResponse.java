package testsystem.backend.dto;

import jakarta.annotation.Nullable;
import lombok.*;

/**
 * DTO class with many fields about user info for profile info response for teacher and student.
 */
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
