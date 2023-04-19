package testsystem.backend.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoForRatingDownload {

    @Getter
    private Integer ratingPosition;
    @Getter
    private String lastName;
    @Getter
    private String firstName;
    @Getter
    private String email;
    @Getter
    private String department;
    @Getter
    private String groupName;

    @Override
    public String toString() {
        return "Rating position;Lastname;First name;Email;Department;Group name";
    }
}
