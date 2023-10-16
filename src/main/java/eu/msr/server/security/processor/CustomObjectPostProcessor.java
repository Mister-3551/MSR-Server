package eu.msr.server.security.processor;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import java.util.Collection;

public class CustomObjectPostProcessor implements ObjectPostProcessor, FilterInvocationSecurityMetadataSource {

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public Object postProcess(Object object) {
        return object;
    }
}