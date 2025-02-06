package com.dylanwraith.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @GetMapping("/")
    public ResponseEntity<String> publicPage() {
        return ResponseEntity.ok("This page is for all users, authenticated or not.");
    }

    @GetMapping("/private")
    public ResponseEntity<String> privatePage(
            @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient client,
            @AuthenticationPrincipal OidcUser principal) {

        return ResponseEntity.ok("This page is for authenticated users.");
    }

    @GetMapping("/private-admin")
    public ResponseEntity<String> privateScopedPage(
            @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient client,
            @AuthenticationPrincipal OidcUser principal) {

        return ResponseEntity.ok("This page is for Admin users.");
    }

}
