package api.iam.scope.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "scopes")
public class Scope {

    @Id
    @Column(name = "scope_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID scopeId;

    @Column(name = "scope_name", nullable = false)
    private String scopeName;

    public UUID getScopeId() {
        return scopeId;
    }

    public void setScopeId(UUID scopeId) {
        this.scopeId = scopeId;
    }

    public String getScopeName() {
        return scopeName;
    }

    public void setScopeName(String scopeName) {
        this.scopeName = scopeName;
    }

    @Override
    public String toString() {
        return "Scope ["
            + "scopeId=" + scopeId
            + ", scopeName=" + scopeName
            + "]";
    }
}
