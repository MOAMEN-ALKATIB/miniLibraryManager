package de.moamen.json;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface JsonConverter {
    <T> String toJson(T t);
    <T> T fromJson(String json,Class<T> clazz) throws JsonProcessingException;
}
