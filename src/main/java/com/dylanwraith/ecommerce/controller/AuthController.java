package com.dylanwraith.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/")
    public ResponseEntity<String> homePage(@AuthenticationPrincipal OidcUser principal) {
        return ResponseEntity.ok(principal.getEmail());
    }

}
