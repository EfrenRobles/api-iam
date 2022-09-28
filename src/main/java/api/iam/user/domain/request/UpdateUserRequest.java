package api.iam.user.domain.request;

import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern.Flag;

import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

public class UpdateUserRequest {

    @Nullable
    private UUID userId;

    @Nullable
    @Length(min = 2, max = 16, message = "The length of user name must be between 2 and 16 characters.")
    private String userFirstName;

    @Nullable
    @Length(min = 2, max = 16, message = "The length of user name must be between 2 and 16 characters.")
    private String userLastName;

    @Nullable
    @Email(message = "The email address is invalid.", flags = { Flag.CASE_INSENSITIVE })
    private String userEmail;

    @Nullable
    @Length(min = 8, max = 64, message = "The length of the password must be between 8 and 64 characters.")
    private String userPassword;

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

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public String toString() {
        return "UpdateUserCustomRequest ["
            + "userId=" + userId
            + ", userFirstName=" + userFirstName
            + ", userLastName=" + userLastName
            + ", userEmail=" + userEmail
            + ", userPassword=" + userPassword
            + "]";
    }
}
