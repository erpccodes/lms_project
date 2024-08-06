package com.lms.user.converter;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class SimpleGrantedAuthorityConverter implements AttributeConverter<SimpleGrantedAuthority, String> {

	@Override
    public String convertToDatabaseColumn(SimpleGrantedAuthority authority) {
        if (authority == null) {
            return "ROLE_USER";
        }
        return authority.getAuthority();
    }

    @Override
    public SimpleGrantedAuthority convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return new SimpleGrantedAuthority("ROLE_USER");
        }
        return new SimpleGrantedAuthority(dbData);
    }

}
