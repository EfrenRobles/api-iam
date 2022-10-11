package api.iam.scope.domain.request;

import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern.Flag;

import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

public class UpdateScopeRequest {

    @Nullable
    private UUID scopeId;

    @Nullable
    @Length(min = 4, max = 24, message = "The length of scope name must be between 4 and 24 characters.")
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
        return "UpdateScopeCustomRequest ["
            + "scopeId=" + scopeId
            + ", scopeName=" + scopeName
            + "]";
    }
}
