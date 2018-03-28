package io.ermdev.classify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@EnableOAuth2Sso
@SpringBootApplication
public class ClassifyUiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClassifyUiApplication.class, args);
    }

    @Controller
    static class HomeController {
        @Autowired
        OAuth2RestTemplate restTemplate;

        @RequestMapping("/")
        String home(Model model, @RequestParam(value = "code", required = false) String code) {
            System.out.println(restTemplate.getAccessToken());
            List<User> users = Arrays.asList(restTemplate.getForObject("http://localhost:5001/api/users", User[].class));
            model.addAttribute("users", users);
            return "index";
        }
    }

    public static class User {
        public String username;
        public String password;
    }

    @Bean
    OAuth2RestTemplate oauth2RestTemplate(OAuth2ClientContext oauth2ClientContext, OAuth2ProtectedResourceDetails details) {
        return new OAuth2RestTemplate(details, oauth2ClientContext);
    }
}
