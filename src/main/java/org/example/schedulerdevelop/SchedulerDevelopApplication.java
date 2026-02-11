package org.example.schedulerdevelop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SchedulerDevelopApplication {
    public static void main(String[] args) {
        SpringApplication.run(SchedulerDevelopApplication.class, args);
    }
}
