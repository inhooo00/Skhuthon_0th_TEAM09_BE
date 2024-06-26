package com.skhuthon.skhuthon_0th_team9;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableCaching
@EnableJpaAuditing
@SpringBootApplication
public class Skhuthon0thTeam9Application {

    public static void main(String[] args) {
        SpringApplication.run(Skhuthon0thTeam9Application.class, args);
    }

}
