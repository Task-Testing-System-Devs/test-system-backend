package testsystem.backend.dto;

import jakarta.annotation.Nullable;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {

    @NonNull
    private String first_name;

    @NonNull
    private String last_name;

    @Nullable
    private String middle_name;

    @NonNull
    private String role;

    @NonNull
    private String department;

    @NonNull
    private String group;

    @NonNull
    private String email;

    @NonNull
    private String password;

}
