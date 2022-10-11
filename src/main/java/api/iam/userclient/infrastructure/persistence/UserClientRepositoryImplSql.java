package api.iam.userclient.infrastructure.persistence;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import api.shared.domain.Logger;
import api.shared.domain.exception.RepositoryException;
import api.iam.userclient.domain.UserClient;
import api.iam.userclient.domain.UserClientId;

@Component
public class UserClientRepositoryImplSql implements api.iam.userclient.domain.UserClientRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserClientRepository userClientRepository;

    @Override
    public UserClient findById(UserClientId userClientId) throws Exception {
        try {

            return userClientRepository
                .findById(userClientId)
                .stream()
                .findAny()
                .orElse(null);
        } catch (Exception e) {
            Logger.log(e.getMessage());

            throw new RepositoryException("Internal Server Error");
        }
    }

    @Override
    public UserClient save(UserClient userClient) throws Exception {
        try {

            return userClientRepository.save(userClient);
        } catch (Exception e) {
            Logger.log(e.getMessage());

            throw new RepositoryException("Internal Server Error");
        }
    }
}
