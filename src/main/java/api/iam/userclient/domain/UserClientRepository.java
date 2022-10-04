package api.iam.userclient.domain;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserClientRepository {

    public UserClient save(UserClient userClient) throws Exception;
}
