# ecommerce

### Local APP Development Setup
1. Install Node.js
2. Navigate to `ecommerce/app`
3. Run `npm install`
4. Run `npm start`
5. App should start at `localhost:3000` unless configured for a different port

### Local API Development Setup
1. Install MySQL 8.0.41
2. Start MySQL 
3. Create `ecommerce` MySQL table 
4. Add environment variables for `ECOMMERCE_MYSQL_DB_USER` and `ECOMMERCE_MYSQL_DB_PASSWORD`
5. Create MySQL user for `ECOMMERCE_MYSQL_DB_USER` and `ECOMMERCE_MYSQL_DB_PASSWORD`
6. Go through Auth0 setup and configure respective environment variables
7. Run `./gradlew start` from `ecommerce/api`
8. Api should start at `localhost:8080` unless configured for a different port

### Auth0 Setup
1. Create Auth0 account
2. Create Auth0 Application of type Regular Web Application
   1. Take note of Client ID, Client Secret, and Domain
   2. Add http://localhost:8080/login/oauth2/code/okta and http://localhost:8080/authorize/oauth2/code/okta to Allowed Callback URLs
   3. Add http://localhost:8080 to Allowed Logout URLs
   4. Add http://localhost:8080 to Allowed Web Origins 
3. Create Auth0 API
   1. Give a standard identifier like https://ecommerce-api.com, this will be your effective "audience" during authorization
   2. Ensure JWT Profile is Auth0 and Signing Algorithm is RS256
   3. Enable RBAC
   4. Toggle "Add Permissions in the Access Token"
4. Create Auth0 Roles
   1. Create role for Admin
   2. Create role for Customer
      1. Ensure names begin with "ROLE_" ie "ROLE_admin"
5. Create Auth0 Action - Trigger for Post-Login
   1. Drag between `Start` amd `Complete`
   2. Code for Trigger is as follows. This links roles to tokens. Namespace can be anything, will be used for mapping in Spring Security.
    ``` 
      exports.onExecutePostLogin = async (event, api) => {
          const namespace = 'https://ecommerceapi.com';
          if (event.authorization) {
              console.log('Adding username and roles to login tokens.')
              api.idToken.setCustomClaim('preferred_username', event.user.email);
              api.idToken.setCustomClaim(`${namespace}/roles`, event.authorization.roles);
              api.accessToken.setCustomClaim(`${namespace}/roles`, event.authorization.roles);
          }
      }
    ```
6. In Spring Boot application, add the following Gradle/Maven dependencies:
```
implementation("com.okta.spring:okta-spring-boot-starter:3.0.5")
implementation("org.springframework.boot:spring-boot-starter-security")
implementation("org.springframework.boot:spring-boot-starter-web")
```
7. Update `application.properties` with the following
   1. AUTH0_DOMAIN is the domain field of your Auth0 Application
   2. AUTH0_CLIENT_ID is the Client ID of your Auth0 Application
   3. AUTH0_CLIENT_SECRET is the Client Secret of your Auth0 Application
   4. AUTH0_AUDIENCE is the API Audience of your Auth0 API
   5. AUTH0_TRIGGER_NAMESPACE is the namespace defined in the JS code of your Auth0 Action Trigger
```
okta.oauth2.issuer=https://${AUTH0_DOMAIN}/
okta.oauth2.client-id=${AUTH0_CLIENT_ID}
okta.oauth2.client-secret=${AUTH0_CLIENT_SECRET}
okta.oauth2.audience=${AUTH0_AUDIENCE}
okta.oauth2.groupsClaim=${AUTH0_TRIGGER_NAMESPACE}/roles
```
8. Create a `security` package, then create a class within called `SecurityConfiguration` to configure OAuth connection between Spring Boot Application and Auth0:
```
package com.example.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.function.Consumer;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableMethodSecurity
@Configuration
public class SecurityConfiguration {

    @Value("${okta.oauth2.issuer}")
    private String issuer;

    @Value("${okta.oauth2.client-id}")
    private String clientId;

    @Value("${okta.oauth2.audience}")
    private String audience;

    private final ClientRegistrationRepository clientRegistrationRepository;

    public SecurityConfiguration(ClientRegistrationRepository clientRegistrationRepository) {
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Secure endpoints
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/private-admin").hasRole("admin")
                        .anyRequest().authenticated()
                )
                // Manually map `audience` for authorization requests to Auth0
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(authorization -> authorization
                                .authorizationRequestResolver(
                                        authorizationRequestResolver(this.clientRegistrationRepository)
                                )
                        )
                )
                // Quality of life / UX enhancements
                .oauth2ResourceServer(jwt -> jwt.jwt(withDefaults()))
                .logout(logout -> logout.addLogoutHandler(logoutHandler()));

        return http.build();
    }

    private OAuth2AuthorizationRequestResolver authorizationRequestResolver(
            ClientRegistrationRepository clientRegistrationRepository) {

        DefaultOAuth2AuthorizationRequestResolver authorizationRequestResolver =
                new DefaultOAuth2AuthorizationRequestResolver(
                        clientRegistrationRepository, "/oauth2/authorization");
        authorizationRequestResolver.setAuthorizationRequestCustomizer(
                authorizationRequestCustomizer());

        return authorizationRequestResolver;
    }

    private Consumer<OAuth2AuthorizationRequest.Builder> authorizationRequestCustomizer() {
        return customizer -> customizer
                .additionalParameters(params -> params.put("audience", audience));
    }

    private LogoutHandler logoutHandler() {
        return (request, response, authentication) -> {
            try {
                String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
                response.sendRedirect(issuer + "v2/logout?client_id=" + clientId + "&returnTo=" + baseUrl);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
```
9. Following this example:
   1. Endpoint "/" does not require authentication
   2. Endpoint "/private" (or anything besides "/private-admin") will require authorization, but not authentication
   3. Endpoint "/private-admin" will require authorization with ADMIN role
      1. Users can be given roles within Auth0 for testing