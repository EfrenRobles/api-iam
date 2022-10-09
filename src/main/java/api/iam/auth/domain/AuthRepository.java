package api.iam.auth.domain;

import java.util.UUID;

public interface AuthRepository {

    public UserAuth findByUserEmail(String userEnail) throws Exception;
    
    public UserAuth findByUserId(UUID userId);
}
