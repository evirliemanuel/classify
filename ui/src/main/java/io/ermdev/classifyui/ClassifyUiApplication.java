package io.ermdev.classifyui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;

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
//            List<Message> messages = Arrays.asList(restTemplate.getForObject("http://localhost:5001/api/messages", Message[].class));
//            model.addAttribute("messages", messages);
            System.out.println(code);
            return "index";
        }

        @RequestMapping(path = "messages", method = RequestMethod.POST)
        String postMessages(@RequestParam String text) {
            Message message = new Message();
            message.text = text;
            restTemplate.exchange(RequestEntity
                    .post(UriComponentsBuilder.fromHttpUrl("http://localhost:5001/api").pathSegment("messages").build().toUri())
                    .body(message), Message.class);
            return "redirect:/";
        }
    }

    public static class Message {
        public String text;
        public String username;
        public LocalDateTime createdAt;
    }

    @Bean
    OAuth2RestTemplate oauth2RestTemplate(OAuth2ClientContext oauth2ClientContext, OAuth2ProtectedResourceDetails details) {
        return new OAuth2RestTemplate(details, oauth2ClientContext);
    }
}
