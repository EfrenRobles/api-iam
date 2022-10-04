package api.iam.userclient.domain;

import java.io.Serializable;
import java.util.UUID;

public class UserClientId implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID userId;
    private UUID clientId;

    public UserClientId() {
        super();
    }

    public UserClientId(UUID userId, UUID clientId) {
        super();
        this.userId = userId;
        this.clientId = clientId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }
}
