package org.complete.challang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ChallangApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChallangApplication.class, args);
    }

}
