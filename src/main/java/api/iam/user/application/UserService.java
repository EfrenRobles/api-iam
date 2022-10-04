package api.iam.user.application;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import api.iam.user.domain.request.AddUserRequest;
import api.iam.user.domain.request.UpdateUserRequest;
import api.iam.user.domain.response.UserResponse;
import api.shared.domain.response.PaginationResponse;

public interface UserService {

    public UserResponse getUser(UUID userId) throws Exception;

    public PaginationResponse getAllUser(Pageable pageable, UserResponse user);

    public UserResponse addUser(AddUserRequest request) throws Exception;

    public Object updateUser(UpdateUserRequest request) throws Exception;

    public UUID deleteUser(UUID userId) throws Exception;
}