package testsystem.backend.dto;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO class with fields email and password for authentication request.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {

    @Nonnull
    private String email;

    @Nonnull
    private String password;

}
