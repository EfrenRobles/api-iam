package api.iam.role.infrastructure.persistence;

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

import api.iam.role.domain.Role;
import api.iam.role.infrastructure.DomainPersistence;

@Component
public class RoleRepositoryImplSql implements DomainPersistence {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Page<Role> findAll(Pageable pageable, Role role) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Role> criteriaQuery = criteriaBuilder.createQuery(Role.class);
        Root<Role> root = criteriaQuery.from(criteriaQuery.getResultType());

        List<Predicate> conditions = new ArrayList<>();

        if (role.getRoleName() != null) {
            conditions.add(criteriaBuilder.equal(root.get("roleName"), role.getRoleName()));
        }

        criteriaQuery.where(conditions.toArray(new Predicate[conditions.size()]));

        try {

            return new Pagination<Role>(
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
    public Role findByRoleId(UUID roleId) {
        try {

            return roleRepository.findByRoleId(roleId);
        } catch (Exception e) {
            Logger.log(e.getMessage());
            throw new RepositoryException("Internal Server Error");
        }
    }

    @Override
    public Role save(Role role) throws Exception {
        try {

            return roleRepository.save(role);
        } catch (Exception e) {
            Logger.log(e.getMessage());

            // ERROR: duplicate key value violates unique constraint "roles_un"
            if (e.getMessage().contains("constraint [roles_un]")) {
                return null;
            }
            
            throw new RepositoryException("Internal Server Error");
        }
    }

    @Override
    public void delete(Role role) throws Exception {
        try {
            roleRepository.delete(role);
        } catch (Exception e) {
            Logger.log(e.getMessage());
            throw new RepositoryException("Internal Server Error");
        }
    }
}
