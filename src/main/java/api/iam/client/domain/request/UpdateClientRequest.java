package api.iam.client.domain.request;

import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern.Flag;

import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

public class UpdateClientRequest {

    @Nullable
    private UUID clientId;

    @Nullable
    @Length(min = 5, max = 5, message = "The length of client code must be 5 characters.")
    private String clientCode;

    @Nullable
    @Length(min = 5, max = 64, message = "The length of client name must be between 5 and 44 characters.")
    private String clientName;

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public String toString() {
        return "UpdateClientCustomRequest ["
            + "clientId=" + clientId
            + ", clientName=" + clientName
            + "]";
    }
}
