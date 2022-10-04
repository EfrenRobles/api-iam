package api.iam.userclient.application;

import org.springframework.http.ResponseEntity;

import api.iam.user.domain.request.UpdateUserRequest;

public interface UserClientService {

    public ResponseEntity<?> updateUserClient(UpdateUserRequest data) throws Exception;
}
