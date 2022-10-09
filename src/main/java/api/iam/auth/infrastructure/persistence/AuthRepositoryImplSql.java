package api.iam.auth.infrastructure.persistence;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import api.shared.domain.Logger;
import api.shared.domain.exception.RepositoryException;
import api.iam.auth.domain.UserAuth;

@Component
public class AuthRepositoryImplSql implements api.iam.auth.domain.AuthRepository {

    @Autowired
    private AuthRepository authRepository;

    @Override
    public UserAuth findByUserEmail(String userEnail) throws Exception {
        try {

            return authRepository.findByUserEmail(userEnail);
        } catch (Exception e) {
            Logger.log(e.getMessage());
            throw new RepositoryException("Internal Server Error");
        }
    }

    @Override
    public UserAuth findByUserId(UUID userId) {
        try {

            return authRepository.findByUserId(userId);
        } catch (Exception e) {
            Logger.log(e.getMessage());
            throw new RepositoryException("Internal Server Error");
        }
    }

}
