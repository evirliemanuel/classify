package io.ermdev.classify.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientController {

    @GetMapping("/credentials/oauth-client")
    public String credentialsOAuthClient(Model model) {
        return "oauth-client";
    }
}
