package api.iam.auth.domain.response;

import java.util.UUID;

public class UserResponse {

    private UUID userId;

    private String userFirstName;

    private String userLastName;

    private String userEmail;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public String toString() {
        return "User ["
            + "userId=" + userId
            + ", userFirstName=" + userFirstName
            + ", userLastName=" + userLastName
            + ", userEmail=" + userEmail
            + "]";
    }
}
