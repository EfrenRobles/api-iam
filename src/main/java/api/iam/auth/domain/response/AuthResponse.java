package api.iam.auth.domain.response;

import java.util.UUID;

public class AuthResponse {

    private String tokenType = "bearer";

    private String accessToken;

    private String refreshToken;

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "Auth ["
            + "tokenType=" + tokenType
            + ", accessToken=" + accessToken
            + ", refreshToken=" + refreshToken
            + "]";
    }
}
