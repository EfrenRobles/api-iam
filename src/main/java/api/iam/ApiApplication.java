package api.iam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import api.shared.infrastructure.config.EnvConfig;

@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
        EnvConfig.setProperty(
            SpringApplication.run(ApiApplication.class, args).getEnvironment()
        );
    }

}
