package com.skhuthon.skhuthon_0th_team9;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.TimeZone;

@EnableCaching
@EnableJpaAuditing
@SpringBootApplication
public class Skhuthon0thTeam9Application {
    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        SpringApplication.run(Skhuthon0thTeam9Application.class, args);
    }

}
