package api.iam.auth.infrastructure.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.iam.auth.application.AuthService;
import api.iam.auth.domain.request.LoginAuthRequest;
import api.shared.domain.response.OnResponse;

@RestController
@RequestMapping("/api/v1/login")
@Validated
public class AuthController {

    @Autowired
    private AuthService authService;
    
    @PostMapping
    public ResponseEntity<?> getAuthToken(@Valid @RequestBody LoginAuthRequest request) throws Exception {

        return OnResponse.onSuccess(authService.getAuth(request), HttpStatus.OK);
    }
}
