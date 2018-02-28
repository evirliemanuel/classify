package io.ermdev.classify;

import io.ermdev.classify.data.entity.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClassifyApplication {

	public static void main(String[] args) {
		System.out.println(new User(1, "rafael", "123"));
		SpringApplication.run(ClassifyApplication.class, args);
	}
}
