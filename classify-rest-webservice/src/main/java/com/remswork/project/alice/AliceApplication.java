package com.remswork.project.alice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication(scanBasePackages = {"com.remswork.project.alice", "com.remswork.project.alice.resource"})
public class AliceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AliceApplication.class, args);
	}
}
