package api.iam.user.infrastructure.persistence.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import api.IamApplication;
import api.iam.user.application.UserService;
import api.iam.user.application.UserServiceImp;
import api.iam.user.infrastructure.persistence.UserRepositoryImplSql;

@Configuration
@ComponentScan(basePackageClasses = IamApplication.class)
public class UserConfiguration {

    @Bean
    UserService userService(UserRepositoryImplSql repo) {

        return UserServiceImp.build(repo);
    }
}
