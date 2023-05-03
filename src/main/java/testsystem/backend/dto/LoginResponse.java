package testsystem.backend.dto;

import lombok.*;

/**
 * DTO class with fields role and token for login response.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    @NonNull
    private String role;

    @NonNull
    private String token;

}
