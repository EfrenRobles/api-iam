package api.iam.role.infrastructure.persistence.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import api.IamApplication;
import api.iam.role.application.RoleService;
import api.iam.role.application.RoleServiceImp;
import api.iam.role.infrastructure.persistence.RoleRepositoryImplSql;

@Configuration
@ComponentScan(basePackageClasses = IamApplication.class)
public class RoleConfiguration {

    @Bean
    RoleService roleService(RoleRepositoryImplSql repo) {

        return RoleServiceImp.build(repo);
    }
}
