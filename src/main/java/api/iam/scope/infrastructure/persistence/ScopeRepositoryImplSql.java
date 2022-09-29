package api.iam.scope.infrastructure.persistence;

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

import api.iam.scope.domain.Scope;
import api.iam.scope.infrastructure.DomainPersistence;

@Component
public class ScopeRepositoryImplSql implements DomainPersistence {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ScopeRepository scopeRepository;

    @Override
    public Page<Scope> findAll(Pageable pageable, Scope scope) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Scope> criteriaQuery = criteriaBuilder.createQuery(Scope.class);
        Root<Scope> root = criteriaQuery.from(criteriaQuery.getResultType());

        List<Predicate> conditions = new ArrayList<>();

        if (scope.getScopeName() != null) {
            conditions.add(criteriaBuilder.like(root.get("scopeName"), "%" + scope.getScopeName() + "%"));
        }

        criteriaQuery.where(conditions.toArray(new Predicate[conditions.size()]));

        try {

            return new Pagination<Scope>(
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
    public Scope findByScopeId(UUID scopeId) {
        try {

            return scopeRepository.findByScopeId(scopeId);
        } catch (Exception e) {
            Logger.log(e.getMessage());
            throw new RepositoryException("Internal Server Error");
        }
    }

    @Override
    public Scope save(Scope scope) throws Exception {
        try {

            return scopeRepository.save(scope);
        } catch (Exception e) {
            Logger.log(e.getMessage());

            // ERROR: duplicate key value violates unique constraint "scopes_un"
            if (e.getMessage().contains("constraint [scopes_un]")) {
                return null;
            }
            
            throw new RepositoryException("Internal Server Error");
        }
    }

    @Override
    public void delete(Scope scope) throws Exception {
        try {
            scopeRepository.delete(scope);
        } catch (Exception e) {
            Logger.log(e.getMessage());
            throw new RepositoryException("Internal Server Error");
        }
    }
}
