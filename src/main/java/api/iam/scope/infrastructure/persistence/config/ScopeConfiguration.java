package api.iam.scope.infrastructure.persistence.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import api.IamApplication;
import api.iam.scope.application.ScopeService;
import api.iam.scope.application.ScopeServiceImp;
import api.iam.scope.infrastructure.persistence.ScopeRepositoryImplSql;

@Configuration
@ComponentScan(basePackageClasses = IamApplication.class)
public class ScopeConfiguration {

    @Bean
    ScopeService scopeService(ScopeRepositoryImplSql repo) {

        return ScopeServiceImp.build(repo);
    }
}
