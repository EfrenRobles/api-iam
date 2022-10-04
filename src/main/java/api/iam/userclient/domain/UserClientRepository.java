package api.iam.userclient.domain;

import java.util.UUID;

public interface UserClientRepository {

    public UserClient findById(UserClientId userClientId) throws Exception;

    public UserClient save(UserClient userClient) throws Exception;
}
