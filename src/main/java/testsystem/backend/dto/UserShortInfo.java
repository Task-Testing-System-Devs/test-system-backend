package testsystem.backend.dto;

/**
 * DTO record with many fields about user short info.
 */
public record UserShortInfo(
        String email,
        String role,
        String firstName,
        String lastName,
        String middleName,
        String departmentName,
        String groupName
) {}
