package com.numble.webnovelservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WebnovelserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebnovelserviceApplication.class, args);
    }
}
