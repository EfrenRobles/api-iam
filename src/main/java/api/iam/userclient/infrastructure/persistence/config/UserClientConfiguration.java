package api.iam.userclient.infrastructure.persistence.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import api.IamApplication;
import api.iam.userclient.application.UserClientService;
import api.iam.userclient.application.UserClientServiceImp;
import api.iam.userclient.infrastructure.persistence.UserClientRepositoryImplSql;

@Configuration
@ComponentScan(basePackageClasses = IamApplication.class)
public class UserClientConfiguration {

    @Bean
    UserClientService userClientService(UserClientRepositoryImplSql repo) {

        return UserClientServiceImp.build(repo);
    }
}
