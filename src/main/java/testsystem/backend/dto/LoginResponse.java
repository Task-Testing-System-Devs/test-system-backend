package testsystem.backend.dto;

import lombok.*;

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
