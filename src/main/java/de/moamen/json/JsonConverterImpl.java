package de.moamen.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JsonConverterImpl implements JsonConverter{
    private static final ObjectMapper objectMapper=new ObjectMapper();
    private static final Logger logger= LoggerFactory.getLogger(JsonConverterImpl.class);
    @Override
    public <T> String toJson(T t) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(t);
        }catch (JsonProcessingException e){
            logger.error("Error converting object to JSON: {}", e.getMessage());
            return null;
        }
     }

    @Override
    public <T> T fromJson(String json, Class<T> clazz){
        try {
            return objectMapper.readValue(json,clazz);
        }catch (JsonProcessingException e){
            logger.error("Error parsing JSON to {}: {}", clazz.getSimpleName(), e.getMessage());
            return null;
        }
    }
}
