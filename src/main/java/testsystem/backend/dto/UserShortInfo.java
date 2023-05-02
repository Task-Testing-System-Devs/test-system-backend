package testsystem.backend.dto;

public record UserShortInfo(String email, String role,
                            String firstName, String lastName, String middleName,
                            String departmentName, String groupName) {
}
