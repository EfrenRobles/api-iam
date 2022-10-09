package api.iam.auth.application;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;

import api.iam.auth.domain.AuthRepository;
import api.iam.auth.domain.UserAuth;
import api.iam.auth.domain.request.LoginAuthRequest;
import api.iam.auth.domain.response.AuthResponse;
import api.shared.domain.Builder;
import api.shared.domain.exception.ServiceException;
import api.shared.infrastructure.config.EnvConfig;
import api.shared.infrastructure.gateway.AuthGateway;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class AuthServiceImp implements AuthService {

    private final byte DAY = 24;

    private AuthRepository authRepository;

    public AuthServiceImp(AuthRepository authRepository) {

        this.authRepository = authRepository;
    }

    public static AuthServiceImp build(AuthRepository authRepository) {

        return new AuthServiceImp(authRepository);
    }

    @Override
    public AuthResponse getAuth(LoginAuthRequest request) throws Exception {

        UserAuth user = authRepository.findByUserEmail(request.getUserEmail());

        if (user == null || !BCrypt.checkpw(request.getUserPassword(), user.getUserPassword())) {
            throw new ServiceException("The user credential does not match");
        }

        return Builder.set(AuthResponse.class)
            .with(a -> a.setAccessToken(generateToken(user.getUserId(), false)))
            .with(a -> a.setRefreshToken(generateToken(user.getUserId(), true)))
            .build();
    }

    // generate token
    private String generateToken(UUID userId, Boolean isRefresnToken) {

        // We needed to use this dummy role, due the scopes are defined by the user and client.
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

        Map<String, List<String>> claims = new HashMap<>();

        claims.put(
            "authorities",
            grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())
        );

        return doGenerateToken(claims, userId, isRefresnToken);
    }

    // while creating the token.
    // 1. Define claims of the token, like Issuer, Expiration, Subject, and the ID
    // 2. Sign the JWT using the HS512 algorithm and secret key.
    // 3. According to JWS Compact
    // Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    // compaction of the JWT to a URL-safe string
    private String doGenerateToken(Map<String, List<String>> claims, UUID userId, Boolean isRefresnToken) {

        return Jwts
            .builder()
            .setClaims(claims)
            .setSubject(userId.toString())
            .setIssuedAt(getTime(false, false))
            .setExpiration(getTime(true, isRefresnToken))
            .signWith(Keys.hmacShaKeyFor(EnvConfig.getProperty("spring.client.secretKey").getBytes()))
            .compact();
    }

    // for now, accessToken and refresh token are the same but have expiration times.
    private Date getTime(Boolean setExpiration, Boolean isRefresnToken) {
        Long epoch = System.currentTimeMillis();

        if (setExpiration) {
            epoch += 36600000;
        }

        if (isRefresnToken) {
            epoch *= DAY;
        }

        return new Date(epoch);
    }
}
