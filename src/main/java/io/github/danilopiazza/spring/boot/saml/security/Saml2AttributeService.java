package io.github.danilopiazza.spring.boot.saml.security;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.XSString;
import org.opensaml.core.xml.schema.impl.XSAnyImpl;
import org.opensaml.saml.saml2.core.Response;
import org.springframework.security.saml2.provider.service.authentication.Saml2Authentication;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;

import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.xml.BasicParserPool;
import net.shibboleth.utilities.java.support.xml.XMLParserException;

@Service
public class Saml2AttributeService {
    public Map<String, List<String>> getAttributes(Saml2Authentication authentication) {
        Element element = getDocumentElement(authentication);
        Response response = getResponse(element);
        return response.getAssertions().stream().flatMap(assertion -> assertion.getAttributeStatements().stream())
                .flatMap(attributeStatement -> attributeStatement.getAttributes().stream())
                .collect(Collectors.toMap(attribute -> attribute.getName(),
                        attribute -> getAttributeValues(attribute.getAttributeValues())));
    }

    private Element getDocumentElement(Saml2Authentication authentication) {
        try (Reader reader = new StringReader(authentication.getSaml2Response())) {
            BasicParserPool basicParserPool = new BasicParserPool();
            basicParserPool.initialize();
            return basicParserPool.parse(reader).getDocumentElement();
        } catch (ComponentInitializationException | IOException | XMLParserException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private Response getResponse(Element element) {
        try {
            return (Response) XMLObjectProviderRegistrySupport.getUnmarshallerFactory().getUnmarshaller(element)
                    .unmarshall(element);
        } catch (UnmarshallingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private List<String> getAttributeValues(Collection<XMLObject> collection) {
        return collection.stream().map(this::getAttributeValue).collect(Collectors.toList());
    }

    private String getAttributeValue(XMLObject attributeValue) {
        return attributeValue == null ? null
                : attributeValue instanceof XSString ? getStringAttributeValue((XSString) attributeValue)
                        : attributeValue instanceof XSAnyImpl ? getAnyAttributeValue((XSAnyImpl) attributeValue)
                                : attributeValue.toString();
    }

    private String getStringAttributeValue(XSString attributeValue) {
        return attributeValue.getValue();
    }

    private String getAnyAttributeValue(XSAnyImpl attributeValue) {
        return attributeValue.getTextContent();
    }
}
