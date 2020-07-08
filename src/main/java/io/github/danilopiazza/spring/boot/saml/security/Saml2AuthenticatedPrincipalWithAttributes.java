package io.github.danilopiazza.spring.boot.saml.security;

import java.util.List;
import java.util.Map;

import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticatedPrincipal;

public class Saml2AuthenticatedPrincipalWithAttributes implements Saml2AuthenticatedPrincipal {
    private final String name;
    private final Map<String, List<String>> attributes;

    public Saml2AuthenticatedPrincipalWithAttributes(String name, Map<String, List<String>> attributes) {
        this.name = name;
        this.attributes = attributes;
    }

    public String getName() {
        return name;
    }

    public Map<String, List<String>> getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        return "Saml2AuthenticatedPrincipalWithAttributes [attributes=" + attributes + ", name=" + name + "]";
    }
}
