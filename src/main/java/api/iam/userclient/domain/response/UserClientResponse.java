package api.iam.userclient.domain.response;

import java.util.List;
import java.util.UUID;

public class UserClientResponse {

    private UUID userId;

    private UUID clientId;

    private UUID roleId;

    private List<String> scopes;

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

    public UUID getRoleId() {
        return roleId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }

    public List<String> getScopes() {
        return scopes;
    }

    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }

    @Override
    public String toString() {
        return "UserClient ["
            + "userId=" + userId
            + ", clientId=" + clientId
            + ", roleId=" + roleId
            + ", scopes=" + scopes
            + "]";
    }

}
