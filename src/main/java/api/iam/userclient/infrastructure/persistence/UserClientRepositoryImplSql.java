package api.iam.userclient.infrastructure.persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import api.shared.domain.Logger;
import api.shared.domain.exception.RepositoryException;
import api.shared.infrastructure.persistence.Pagination;

import api.iam.userclient.domain.UserClient;

@Component
public class UserClientRepositoryImplSql implements api.iam.userclient.domain.UserClientRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserClientRepository userClientRepository;

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
