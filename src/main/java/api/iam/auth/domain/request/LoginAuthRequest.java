package api.iam.auth.domain.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern.Flag;

import org.hibernate.validator.constraints.Length;

public class LoginAuthRequest {

    @NotBlank(message = "The user email is required.")
    @Email(message = "The email address is invalid.", flags = { Flag.CASE_INSENSITIVE })
    private String userEmail;

    @NotBlank(message = "The user password is required.")
    @Length(min = 8, max = 64, message = "The length of the password must be between 8 and 64 characters.")
    private String userPassword;

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
        return "AddAuthCustomRequest ["
            + ", userEmail=" + userEmail
            + ", userPassword=" + userPassword
            + "]";
    }
}
