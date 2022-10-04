package api.iam.user.application;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;

import api.shared.domain.Builder;
import api.shared.domain.exception.ServiceException;
import api.shared.domain.response.PaginationResponse;
import api.iam.user.domain.UserRepository;
import api.iam.user.domain.User;
import api.iam.user.domain.request.AddUserRequest;
import api.iam.user.domain.request.UpdateUserRequest;
import api.iam.user.domain.response.UserResponse;
import api.iam.userclient.application.UserClientService;
import api.iam.userclient.domain.response.UserClientResponse;

public class UserServiceImp implements UserService {

    // todo: set out dto here instead userClient

    @Autowired
    private UserClientService userClientService;

    private UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public static UserServiceImp build(UserRepository userRepository) {

        return new UserServiceImp(userRepository);
    }

    @Override
    public Object getUser(UUID userId, UUID clientId) throws Exception {

        User user = userRepository.findByUserId(userId);

        if (user == null) {
            throw new ServiceException("User not found");
        }

        if (clientId == null) {
            return mapToUserDto(user);
        }
        
        UserClientResponse response = userClientService.getUserClient(userId, clientId);

        return Builder.set(UpdateUserRequest.class)
                .with(u -> u.setUserId(user.getUserId()))
                .with(u -> u.setClientId(response.getClientId()))
                .with(u -> u.setRoleId(response.getRoleId()))
                .with(u -> u.setUserFirstName(user.getUserFirstName()))
                .with(u -> u.setUserLastName(user.getUserLastName()))
                .with(u -> u.setUserEmail(user.getUserEmail()))
                .with(u -> u.setUserPassword("Secret"))
                .with(u -> u.setScopes(response.getScopes()))
                .build();        
    }

    @Override
    public PaginationResponse getAllUser(Pageable pageable, UserResponse data) {

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

        return Builder.set(PaginationResponse.class)
            .with(p -> p.setData(content))
            .with(p -> p.setPage((short) users.getNumber()))
            .with(p -> p.setLimit((byte) users.getSize()))
            .with(p -> p.setTotalItems((short) users.getTotalElements()))
            .with(p -> p.setTotalPages((short) users.getTotalPages()))
            .with(p -> p.setLast(users.isLast()))
            .build();
    }

    @Override
    public UserResponse addUser(AddUserRequest data) throws Exception {

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

        return mapToUserDto(user);
    }

    @Override
    public Object updateUser(UpdateUserRequest data) throws Exception {
        User user = userRepository.findByUserId(data.getUserId());

        if (ifExist(user, data)) {
            user = userRepository.save(user);
        }

        if (user == null) {
            throw new ServiceException("The user email is already registered");
        }

        UserClientResponse response = userClientService.updateUserClient(data);

        if (response == null) {

            return mapToUserDto(user);
        }

        final User userFinal = user;

        return Builder.set(UpdateUserRequest.class)
            .with(u -> u.setUserId(userFinal.getUserId()))
            .with(u -> u.setClientId(response.getClientId()))
            .with(u -> u.setRoleId(response.getRoleId()))
            .with(u -> u.setUserFirstName(userFinal.getUserFirstName()))
            .with(u -> u.setUserLastName(userFinal.getUserLastName()))
            .with(u -> u.setUserEmail(userFinal.getUserEmail()))
            .with(u -> u.setUserPassword("Secret"))
            .with(u -> u.setScopes(response.getScopes()))
            .build();
    }

    private Boolean ifExist(User user, UpdateUserRequest data) {
        Boolean needUpdate = false;

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

        return needUpdate;
    }

    @Override
    public UUID deleteUser(UUID userId) throws Exception {

        User user = userRepository.findByUserId(userId);

        if (user == null) {
            throw new ServiceException("The user was not found");
        }

        userRepository.delete(user);

        return userId;
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
