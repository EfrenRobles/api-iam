package api.iam.userclient.application;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import api.shared.domain.Builder;
import api.shared.domain.Logger;
import api.shared.domain.exception.ServiceException;
import api.shared.domain.response.OnResponse;
import api.iam.userclient.domain.UserClientRepository;
import api.iam.userclient.domain.response.UserClientResponse;
import api.iam.client.application.ClientService;
import api.iam.role.application.RoleService;
import api.iam.scope.application.ScopeService;
import api.iam.user.domain.User;
import api.iam.user.domain.request.UpdateUserRequest;
import api.iam.user.domain.response.UserResponse;
import api.iam.userclient.domain.UserClient;

public class UserClientServiceImp implements UserClientService {

    @Autowired
    private ClientService clientService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ScopeService scopeService;

    private UserClientRepository userClientRepository;

    public UserClientServiceImp(UserClientRepository userClientRepository) {

        this.userClientRepository = userClientRepository;
    }

    public static UserClientServiceImp build(UserClientRepository userClientRepository) {

        return new UserClientServiceImp(userClientRepository);
    }

    @Override
    public ResponseEntity<?> updateUserClient(UpdateUserRequest data) throws Exception {

        if (!ifExistAllOrNone(data)) {
            return OnResponse.onSuccess(null, HttpStatus.OK);
        }

        clientService.getClient(data.getClientId());
        roleService.getRole(data.getRoleId());
        scopeService.getScopeInScopeName(data.getScopes());

        UserClient userClient = Builder.set(UserClient.class)
            .with(u -> u.setUserId(data.getUserId()))
            .with(u -> u.setClientId(data.getClientId()))
            .with(u -> u.setRoleId(data.getRoleId()))
            .with(u -> u.setScopes(data.getScopes()))
            .build();

        userClient = userClientRepository.save(userClient);

        return OnResponse.onSuccess(mapToUserDto(userClient), HttpStatus.OK);
    }

    private Boolean ifExistAllOrNone(UpdateUserRequest data) {

        if (data.getClientId() != null && data.getRoleId() != null && data.getScopes() != null) {
            return true;
        }

        if (data.getClientId() == null && data.getRoleId() == null && data.getScopes() == null) {
            return false;
        }

        if (data.getClientId() != null) {
            throw new ServiceException("The roleId and scopes must not be null");
        }

        if (data.getRoleId() != null) {
            throw new ServiceException("The userId and scopes must not be null");
        }

        if (data.getScopes() != null) {
            throw new ServiceException("The userId and roleId must not be null");
        }

        if (data.getScopes().size() < 1) {
            throw new ServiceException("The scope list must not be empty");
        }

        return false;
    }



    private UserClientResponse mapToUserDto(UserClient data) {

        return Builder.set(UserClientResponse.class)
            .with(u -> u.setUserId(data.getUserId()))
            .with(u -> u.setClientId(data.getClientId()))
            .with(u -> u.setRoleId(data.getRoleId()))
            .with(u -> u.setScopes(data.getScopes()))
            .build();
    }
}
