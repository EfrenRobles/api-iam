package api.iam.userclient.application;

import java.util.UUID;

import org.springframework.http.ResponseEntity;

import api.iam.user.domain.request.UpdateUserRequest;
import api.iam.userclient.domain.response.UserClientResponse;

public interface UserClientService {

    public UserClientResponse getUserClient(UUID userId, UUID clientId) throws Exception;

    public UserClientResponse updateUserClient(UpdateUserRequest data) throws Exception;
}
