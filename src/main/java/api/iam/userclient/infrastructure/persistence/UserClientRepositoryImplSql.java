package api.iam.userclient.infrastructure.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import api.shared.domain.Logger;
import api.shared.domain.exception.RepositoryException;
import api.iam.userclient.domain.UserClient;

@Component
public class UserClientRepositoryImplSql implements api.iam.userclient.domain.UserClientRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserClientRepository userClientRepository;

    // pending get

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
