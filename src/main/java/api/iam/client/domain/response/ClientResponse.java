package api.iam.client.domain.response;

import java.util.UUID;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

public class ClientResponse {

    private UUID clientId;

    private String clientCode;

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
        return "Client ["
            + "clientId=" + clientId
            + ", clientName=" + clientName
            + "]";
    }
}
