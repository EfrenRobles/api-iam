package api.iam.scope.domain.response;

import java.util.UUID;

public class ScopeResponse {

    private UUID scopeId;

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
