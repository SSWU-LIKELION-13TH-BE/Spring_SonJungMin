package com.example.week9.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.List;

@Converter
public class ImageListConverter implements AttributeConverter<List<String>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<String> images) {
        try {
            return objectMapper.writeValueAsString(images);
        } catch (Exception e) {
            throw new IllegalArgumentException("이미지 리스트를 JSON 문자열로 변환 실패", e);
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            throw new IllegalArgumentException("JSON 문자열을 이미지 리스트로 변환 실패", e);
        }
    }
}
