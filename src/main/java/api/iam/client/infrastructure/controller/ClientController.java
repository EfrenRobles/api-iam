package api.iam.client.infrastructure.controller;

import java.util.UUID;

import javax.validation.Valid;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import api.iam.client.application.ClientService;
import api.iam.client.domain.request.AddClientRequest;
import api.iam.client.domain.request.UpdateClientRequest;
import api.iam.client.domain.response.ClientResponse;
import api.shared.domain.Builder;
import api.shared.domain.response.OnResponse;
import api.shared.application.PageService;
import api.shared.infrastructure.PaginationConstant;

@RestController
@RequestMapping("/api/v1/client")
@Validated
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(params = "clientId")
    public ResponseEntity<?> getClientByClientId(@RequestParam(value = "clientId") UUID clientId) throws Exception {

        return OnResponse.onSuccess(clientService.getClient(clientId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getClientList(
        @RequestParam(value = "page", defaultValue = PaginationConstant.PAGE_DEFAULT, required = false) Short page,
        @RequestParam(value = "limit", defaultValue = PaginationConstant.LIMIT_DEFAULT, required = false) Byte limit,
        @RequestParam(value = "sortBy", defaultValue = "clientId", required = false) String sortBy,
        @RequestParam(value = "sortDir", defaultValue = PaginationConstant.SORT_ASC, required = false) String sortDir,
        @RequestParam(value = "clientCode", required = false) @Length(min = 1, max = 5) String clientCode,
        @RequestParam(value = "clientName", required = false) @Length(min = 3, max = 64) String clientName
    ) {
        Pageable pageable = Builder.set(PageService.class)
            .with(p -> p.setPage(page))
            .with(p -> p.setLimit(limit))
            .with(p -> p.setSortBy(sortBy))
            .with(p -> p.setSortDir(sortDir))
            .build()
            .getPageable();

        ClientResponse client = Builder.set(ClientResponse.class)
            .with(u -> u.setClientCode(clientCode))
            .with(u -> u.setClientName(clientName))
            .build();

        return OnResponse.onSuccessPagination(clientService.getAllClient(pageable, client), HttpStatus.OK);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> postClient(@Valid @RequestBody AddClientRequest client) throws Exception {

        return OnResponse.onSuccess(clientService.addClient(client), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping
    public ResponseEntity<?> patchClient(
        @RequestParam UUID clientId,
        @Valid @RequestBody UpdateClientRequest client
    ) throws Exception {
        client.setClientId(clientId);

        return OnResponse.onSuccess(clientService.updateClient(client), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public ResponseEntity<?> deleteClient(@RequestParam UUID clientId) throws Exception {

        return OnResponse.onSuccess(clientService.deleteClient(clientId), HttpStatus.NO_CONTENT);
    }
}
