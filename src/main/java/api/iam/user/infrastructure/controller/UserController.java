package api.iam.user.infrastructure.controller;

import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern.Flag;

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

import api.iam.user.application.UserService;
import api.iam.user.domain.request.AddUserRequest;
import api.iam.user.domain.request.UpdateUserRequest;
import api.iam.user.domain.response.UserResponse;
import api.shared.application.PageService;
import api.shared.domain.Builder;
import api.shared.domain.response.OnResponse;
import api.shared.infrastructure.PaginationConstant;
import api.shared.infrastructure.annotation.Scope;

@RestController
@RequestMapping("/api/v1/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Scope(value = "USER.VIEW")
    @GetMapping(params = "userId")
    public ResponseEntity<?> getUserByUserId(
        @RequestParam(value = "userId") UUID userId,
        @RequestParam(value = "clientId", required = false) UUID clientId,
        @RequestParam(value = "isRequest", required = false) Boolean isRequest
    ) throws Exception {

        return OnResponse.onSuccess(userService.getUser(userId, clientId), HttpStatus.OK);
    }

    @Scope(value = "USER.VIEW.LIST")
    @GetMapping
    public ResponseEntity<?> getUserList(
        @RequestParam(value = "page", defaultValue = PaginationConstant.PAGE_DEFAULT, required = false) Short page,
        @RequestParam(value = "limit", defaultValue = PaginationConstant.LIMIT_DEFAULT, required = false) Byte limit,
        @RequestParam(value = "sortBy", defaultValue = "userId", required = false) String sortBy,
        @RequestParam(value = "sortDir", defaultValue = PaginationConstant.SORT_ASC, required = false) String sortDir,
        @RequestParam(value = "userFirstName", required = false) String userFirstName,
        @RequestParam(value = "userLastName", required = false) String userLastName,
        @RequestParam(value = "eventEmail", required = false)
        @Email(flags = { Flag.CASE_INSENSITIVE }) String userEmail
    ) {
        Pageable pageable = Builder.set(PageService.class)
            .with(p -> p.setPage(page))
            .with(p -> p.setLimit(limit))
            .with(p -> p.setSortBy(sortBy))
            .with(p -> p.setSortDir(sortDir))
            .build()
            .getPageable();

        UserResponse user = Builder.set(UserResponse.class)
            .with(u -> u.setUserFirstName(userFirstName))
            .with(u -> u.setUserLastName(userLastName))
            .with(u -> u.setUserEmail(userEmail))
            .build();

        return OnResponse.onSuccessPagination(userService.getAllUser(pageable, user), HttpStatus.OK);
    }

    @Scope(value = "USER.ADD")
    @PostMapping
    public ResponseEntity<?> postUser(@Valid @RequestBody AddUserRequest user) throws Exception {

        return OnResponse.onSuccess(userService.addUser(user), HttpStatus.CREATED);
    }

    @Scope(value = "USER.EDIT")
    @PatchMapping
    public ResponseEntity<?> patchUser(
        @RequestParam UUID userId,
        @Valid @RequestBody UpdateUserRequest user
    ) throws Exception {
        user.setUserId(userId);
        
        return OnResponse.onSuccess(userService.updateUser(user), HttpStatus.OK);
    }

    @Scope(value = "USER.DELETE")
    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestParam UUID userId) throws Exception {

        return OnResponse.onSuccess(userService.deleteUser(userId), HttpStatus.NO_CONTENT);
    }
}
