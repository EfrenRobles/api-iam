package api.iam.userclient.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

@Entity
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Table(name = "user_clients")
@IdClass(UserClientId.class)
public class UserClient {

    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Id
    @Column(name = "client_id")
    private UUID clientId;

    @Column(name = "role_id")
    private UUID roleId;

    @Type(type = "jsonb")
    @Column(name = "scopes", columnDefinition = "jsonb")
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
