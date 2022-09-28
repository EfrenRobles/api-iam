package api.iam.user.application;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import api.iam.user.domain.request.AddUserRequest;
import api.iam.user.domain.request.UpdateUserRequest;
import api.iam.user.domain.response.UserResponse;
import api.shared.domain.application.PageService;

public interface UserService {

	public ResponseEntity<?> getUser(UUID userId) throws Exception;

	public ResponseEntity<?> getAllUser(Pageable pageable, UserResponse user);

	public ResponseEntity<?> addUser(AddUserRequest request) throws Exception;

	public ResponseEntity<?> updateUser(UpdateUserRequest request) throws Exception;
	
	public ResponseEntity<?> deleteUser(UUID userId) throws Exception;
}