package api.iam.client.infrastructure.persistence.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import api.IamApplication;
import api.iam.client.application.ClientService;
import api.iam.client.application.ClientServiceImp;
import api.iam.client.infrastructure.persistence.ClientRepositoryImplSql;

@Configuration
@ComponentScan(basePackageClasses = IamApplication.class)
public class ClientConfiguration {

    @Bean
    ClientService clientService(ClientRepositoryImplSql repo) {

        return ClientServiceImp.build(repo);
    }
}
