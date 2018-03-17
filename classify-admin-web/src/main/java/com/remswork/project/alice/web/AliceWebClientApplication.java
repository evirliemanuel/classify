package com.remswork.project.alice.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.remswork.project.alice.web"})
public class AliceWebClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(AliceWebClientApplication.class, args);
    }
}
