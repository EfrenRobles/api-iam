package api.iam.client.infrastructure.persistence;

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

import api.iam.client.domain.Client;

@Component
public class ClientRepositoryImplSql implements api.iam.client.domain.ClientRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Page<Client> findAll(Pageable pageable, Client client) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
        Root<Client> root = criteriaQuery.from(criteriaQuery.getResultType());

        List<Predicate> conditions = new ArrayList<>();

        if (client.getClientCode() != null) {
            conditions.add(criteriaBuilder.like(root.get("clientCode"), "%" + client.getClientCode() + "%"));
        }

        if (client.getClientName() != null) {
            conditions.add(criteriaBuilder.like(root.get("clientName"), "%" + client.getClientName() + "%"));
        }

        criteriaQuery.where(conditions.toArray(new Predicate[conditions.size()]));

        try {

            return new Pagination<Client>(
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
    public Client findByClientId(UUID clientId) {
        try {

            return clientRepository.findByClientId(clientId);
        } catch (Exception e) {
            Logger.log(e.getMessage());
            throw new RepositoryException("Internal Server Error");
        }
    }

    @Override
    public Client save(Client client) throws Exception {
        try {

            return clientRepository.save(client);
        } catch (Exception e) {
            Logger.log(e.getMessage());

            // ERROR: duplicate key value violates unique constraint "clients_un"
            if (e.getMessage().contains("constraint [clients_un]")) {
                return null;
            }
            
            throw new RepositoryException("Internal Server Error");
        }
    }

    @Override
    public void delete(Client client) throws Exception {
        try {
            clientRepository.delete(client);
        } catch (Exception e) {
            Logger.log(e.getMessage());
            throw new RepositoryException("Internal Server Error");
        }
    }
}
