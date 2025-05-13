package com.pe.platform.vehicle.common.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Converter
public class ListToStringConverter implements AttributeConverter<List<String>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        if (attribute == null) {
            throw new IllegalArgumentException("List cannot be null");
        }
        
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error serializing list of images", e);
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            throw new IllegalArgumentException("JSON string cannot be null");
        }
        
        try {
            return Arrays.asList(objectMapper.readValue(dbData, String[].class));
        } catch (IOException e) {
            throw new IllegalArgumentException("Error deserializing list of images", e);
        }
    }
}
