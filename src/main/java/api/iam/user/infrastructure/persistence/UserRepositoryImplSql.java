package api.iam.user.infrastructure.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import api.shared.domain.Logger;
import api.shared.domain.exception.RepositoryException;
import api.shared.infrastructure.persistence.Pagination;

import api.iam.user.domain.User;

@Component
public class UserRepositoryImplSql implements api.iam.user.domain.UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<User> findAll(Pageable pageable, User user) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(criteriaQuery.getResultType());

        List<Predicate> conditions = new ArrayList<>();

        if (user.getUserFirstName() != null) {
            conditions.add(criteriaBuilder.equal(root.get("userFirstName"), user.getUserFirstName()));
        }

        if (user.getUserLastName() != null) {
            conditions.add(criteriaBuilder.equal(root.get("userLastName"), user.getUserLastName()));
        }

        if (user.getUserEmail() != null) {
            conditions.add(criteriaBuilder.equal(root.get("userEmail"), user.getUserEmail()));
        }

        criteriaQuery.where(conditions.toArray(new Predicate[conditions.size()]));

        try {

            return new Pagination<User>(
                entityManager,
                criteriaBuilder,
                criteriaQuery,
                pageable
            ).getPagination();

        } catch (Exception e) {
            Logger.log("Error by: " + e.getMessage());

            return new PageImpl<>(new ArrayList<>());
        }
    }

    @Override
    public User findByUserId(UUID userId) {
        try {

            return userRepository.findByUserId(userId);
        } catch (Exception e) {
            Logger.log(e.getMessage());
            throw new RepositoryException("Internal Server Error");
        }
    }

    @Override
    public User findByUserEmail(String userEnail) throws Exception {

        try {

            return userRepository.findByUserEmail(userEnail);
        } catch (Exception e) {
            Logger.log(e.getMessage());
            throw new RepositoryException("Internal Server Error");
        }
    }

    @Override
    public User save(User user) throws Exception {
        try {

            return userRepository.save(user);
        } catch (Exception e) {
            Logger.log(e.getMessage());

            // ERROR: duplicate key value violates unique constraint "users_un"
            if (e.getMessage().contains("constraint [users_un]")) {
                return null;
            }
            
            throw new RepositoryException("Internal Server Error");
        }
    }

    @Override
    public void delete(User user) throws Exception {
        try {
            userRepository.delete(user);
        } catch (Exception e) {
            Logger.log(e.getMessage());
            throw new RepositoryException("Internal Server Error");
        }
    }
}
