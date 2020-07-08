package io.github.danilopiazza.spring.boot.saml.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticatedPrincipal;
import org.springframework.security.saml2.provider.service.authentication.Saml2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.danilopiazza.spring.boot.saml.security.Saml2AttributeService;
import io.github.danilopiazza.spring.boot.saml.security.Saml2AuthenticatedPrincipalWithAttributes;

@RestController
public class IndexController {
    @Autowired
    Saml2AttributeService saml2Attributes;

    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Saml2AuthenticatedPrincipalWithAttributes index(
            @CurrentSecurityContext(expression = "authentication") Saml2Authentication authentication,
            @AuthenticationPrincipal Saml2AuthenticatedPrincipal principal) {
        return new Saml2AuthenticatedPrincipalWithAttributes(principal.getName(),
                saml2Attributes.getAttributes(authentication));
    }
}
