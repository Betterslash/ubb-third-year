package ro.ubb.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "ro.ubb.core")
@EnableJpaRepositories(basePackages = "ro.ubb.core")
@SpringBootApplication
public class Start {
    public static void main(String[] args) {
        SpringApplication.run(Start.class, args);
    }
}
