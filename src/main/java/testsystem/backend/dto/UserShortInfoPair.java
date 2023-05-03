package testsystem.backend.dto;

/**
 * DTO record with pair fields: user id and their short personal info.
 */
public record UserShortInfoPair(
        Integer id,
        UserShortInfo userShortInfo
) {}
