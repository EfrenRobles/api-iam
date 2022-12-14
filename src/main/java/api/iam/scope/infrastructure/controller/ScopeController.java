package api.iam.scope.infrastructure.controller;

import java.util.UUID;

import javax.validation.Valid;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import api.iam.scope.application.ScopeService;
import api.iam.scope.domain.request.AddScopeRequest;
import api.iam.scope.domain.request.UpdateScopeRequest;
import api.iam.scope.domain.response.ScopeResponse;
import api.shared.domain.Builder;
import api.shared.domain.response.OnResponse;
import api.shared.application.PageService;
import api.shared.infrastructure.PaginationConstant;
import api.shared.infrastructure.annotation.Scope;

@RestController
@RequestMapping("/api/v1/scope")
@Validated
public class ScopeController {

    @Autowired
    private ScopeService scopeService;

    @Scope(value = "SCOPE.VIEW")
    @GetMapping(params = "scopeId")
    public ResponseEntity<?> getScopeByScopeId(@RequestParam(value = "scopeId") UUID scopeId) throws Exception {

        return OnResponse.onSuccess(scopeService.getScope(scopeId), HttpStatus.OK);
    }

    @Scope(value = "SCOPE.VIEW.LIST")
    @GetMapping
    public ResponseEntity<?> getScopeList(
        @RequestParam(value = "page", defaultValue = PaginationConstant.PAGE_DEFAULT, required = false) Short page,
        @RequestParam(value = "limit", defaultValue = PaginationConstant.LIMIT_DEFAULT, required = false) Byte limit,
        @RequestParam(value = "sortBy", defaultValue = "scopeId", required = false) String sortBy,
        @RequestParam(value = "sortDir", defaultValue = PaginationConstant.SORT_ASC, required = false) String sortDir,
        @RequestParam(value = "scopeName", required = false) @Length(min = 4, max = 24) String scopeName
    ) {
        Pageable pageable = Builder.set(PageService.class)
            .with(p -> p.setPage(page))
            .with(p -> p.setLimit(limit))
            .with(p -> p.setSortBy(sortBy))
            .with(p -> p.setSortDir(sortDir))
            .build()
            .getPageable();

        ScopeResponse scope = Builder.set(ScopeResponse.class)
            .with(u -> u.setScopeName(scopeName))
            .build();

        return OnResponse.onSuccessPagination(scopeService.getAllScope(pageable, scope), HttpStatus.OK);
    }

    @Scope(value = "SCOPE.ADD")
    @PostMapping
    public ResponseEntity<?> postScope(@Valid @RequestBody AddScopeRequest scope) throws Exception {

        return OnResponse.onSuccess(scopeService.addScope(scope), HttpStatus.CREATED);
    }

    @Scope(value = "SCOPE.EDIT")
    @PatchMapping
    public ResponseEntity<?> patchScope(
        @RequestParam UUID scopeId,
        @Valid @RequestBody UpdateScopeRequest scope
    ) throws Exception {
        scope.setScopeId(scopeId);
        
        return OnResponse.onSuccess(scopeService.updateScope(scope), HttpStatus.OK);
    }

    @Scope(value = "SCOPE.DELETE")
    @DeleteMapping
    public ResponseEntity<?> deleteScope(@RequestParam UUID scopeId) throws Exception {

        return OnResponse.onSuccess(scopeService.deleteScope(scopeId), HttpStatus.NO_CONTENT);
    }

}
