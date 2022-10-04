package api.iam.userclient.application;

import org.springframework.http.ResponseEntity;

import api.iam.user.domain.request.UpdateUserRequest;
import api.iam.userclient.domain.response.UserClientResponse;

public interface UserClientService {

    public UserClientResponse updateUserClient(UpdateUserRequest data) throws Exception;
}
