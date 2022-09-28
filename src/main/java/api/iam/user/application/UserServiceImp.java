package api.iam.user.application;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;

import api.shared.domain.Builder;
import api.shared.domain.Logger;
import api.shared.domain.exception.ServiceException;
import api.shared.domain.response.OnResponse;
import api.shared.domain.response.PaginationResponse;
import api.iam.user.domain.UserRepository;
import api.iam.user.domain.User;
import api.iam.user.domain.request.AddUserRequest;
import api.iam.user.domain.request.UpdateUserRequest;
import api.iam.user.domain.response.UserResponse;

public class UserServiceImp implements UserService {

    private UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public static UserServiceImp build(UserRepository userRepository) {

        return new UserServiceImp(userRepository);
    }

    @Override
    public ResponseEntity<?> getUser(UUID userId) throws Exception {

        User user = userRepository.findByUserId(userId);

        if (user == null) {
            throw new ServiceException("User not found");
        }

        return OnResponse.onSuccess(mapToUserDto(user), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllUser(Pageable pageable, UserResponse data) {

        User user = Builder.set(User.class)
            .with(u -> u.setUserFirstName(data.getUserFirstName()))
            .with(u -> u.setUserLastName(data.getUserLastName()))
            .with(u -> u.setUserEmail(data.getUserEmail()))
            .build();

        Page<User> users = userRepository.findAll(pageable, user);

        List<User> userList = users.getContent();
        List<UserResponse> content = userList
            .stream()
            .map(u -> mapToUserDto(u))
            .toList();

        PaginationResponse result = Builder.set(PaginationResponse.class)
            .with(p -> p.setData(content))
            .with(p -> p.setPage((short) users.getNumber()))
            .with(p -> p.setLimit((byte) users.getSize()))
            .with(p -> p.setTotalItems((short) users.getTotalElements()))
            .with(p -> p.setTotalPages((short) users.getTotalPages()))
            .with(p -> p.setLast(users.isLast()))
            .build();

        return OnResponse.onSuccessPagination(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> addUser(AddUserRequest data) throws Exception {

        User user = Builder.set(User.class)
            .with(u -> u.setUserId(UUID.randomUUID()))
            .with(u -> u.setUserFirstName(data.getUserFirstName()))
            .with(u -> u.setUserLastName(data.getUserLastName()))
            .with(u -> u.setUserEmail(data.getUserEmail()))
            .with(u -> u.setUserPassword(BCrypt.hashpw(data.getUserPassword(), BCrypt.gensalt())))
            .build();

        user = userRepository.save(user);

        if (user == null) {
            throw new ServiceException("The user email is already registered");
        }

        return OnResponse.onSuccess(mapToUserDto(user), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> updateUser(UpdateUserRequest data) throws Exception {

        Boolean needUpdate = false;

        User user = userRepository.findByUserId(data.getUserId());

        if (user == null) {
            throw new ServiceException("The user was not found");
        }

        if (data.getUserFirstName() != null && !data.getUserFirstName().equalsIgnoreCase(user.getUserFirstName())) {
            user.setUserFirstName(data.getUserFirstName());
            needUpdate = true;
        }

        if (data.getUserLastName() != null && !data.getUserLastName().equalsIgnoreCase(user.getUserLastName())) {
            user.setUserLastName(data.getUserLastName());
            needUpdate = true;
        }

        if (data.getUserEmail() != null && !data.getUserEmail().equalsIgnoreCase(user.getUserEmail())) {
            user.setUserEmail(data.getUserEmail());
            needUpdate = true;
        }

        if (data.getUserPassword() != null) {
            user.setUserPassword(BCrypt.hashpw(data.getUserPassword(), BCrypt.gensalt()));
            needUpdate = true;
        }

        if (!needUpdate) {
            return OnResponse.onSuccess(mapToUserDto(user), HttpStatus.OK);
        }

        user = userRepository.save(user);

        if (user == null) {
            throw new ServiceException("The user email is already regitered");
        }

        return OnResponse.onSuccess(mapToUserDto(user), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteUser(UUID userId) throws Exception {

        User user = userRepository.findByUserId(userId);

        if (user == null) {
            throw new ServiceException("The user was not found");
        }

        userRepository.delete(user);

        return OnResponse.onSuccess(userId, HttpStatus.NO_CONTENT);
    }

    private UserResponse mapToUserDto(User data) {

        return Builder.set(UserResponse.class)
            .with(u -> u.setUserId(data.getUserId()))
            .with(u -> u.setUserFirstName(data.getUserFirstName()))
            .with(u -> u.setUserLastName(data.getUserLastName()))
            .with(u -> u.setUserEmail(data.getUserEmail()))
            .build();
    }
}
