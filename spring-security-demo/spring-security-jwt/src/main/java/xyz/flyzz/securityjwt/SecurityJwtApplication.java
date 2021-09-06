package xyz.flyzz.securityjwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
public class SecurityJwtApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecurityJwtApplication.class,args);
    }
}