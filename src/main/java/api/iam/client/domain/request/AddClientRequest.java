package api.iam.client.domain.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern.Flag;

import org.hibernate.validator.constraints.Length;

public class AddClientRequest {

    @NotBlank(message = "The client code is required.")
    @Length(min = 5, max = 5, message = "The length of client code must be 5 characters.")
    private String clientCode;

    @NotBlank(message = "The client name is required.")
    @Length(min = 5, max = 64, message = "The length of client name must be between 5 and 64 characters.")
    private String clientName;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    @Override
    public String toString() {
        return "AddClientCustomRequest ["
            + ", clientName=" + clientName
            + "]";
    }
}
