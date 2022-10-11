package api.iam.auth.infrastructure.persistence.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import api.IamApplication;
import api.iam.auth.application.AuthService;
import api.iam.auth.application.AuthServiceImp;
import api.iam.auth.infrastructure.persistence.AuthRepositoryImplSql;

@Configuration
@ComponentScan(basePackageClasses = IamApplication.class)
public class AuthConfiguration {

    @Bean
    AuthService authService(AuthRepositoryImplSql repo) {

        return AuthServiceImp.build(repo);
    }
}
