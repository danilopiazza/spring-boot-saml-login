package io.github.danilopiazza.spring.boot.saml.controller;

import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Saml2AuthenticatedPrincipal index(@AuthenticationPrincipal Saml2AuthenticatedPrincipal principal) {
        return principal;
    }
}
