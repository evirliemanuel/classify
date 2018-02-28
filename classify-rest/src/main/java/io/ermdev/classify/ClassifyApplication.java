package io.ermdev.classify;

import mapfierj.Mapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ClassifyApplication {

    @Bean
    public Mapper getMapper() {
        return new Mapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(ClassifyApplication.class, args);
    }
}
