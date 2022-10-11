package api.iam.auth.application;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import api.iam.auth.domain.request.LoginAuthRequest;
import api.iam.auth.domain.response.AuthResponse;
import api.iam.user.domain.response.UserResponse;

public interface AuthService {

    public AuthResponse getAuth(LoginAuthRequest authId) throws Exception;
}
