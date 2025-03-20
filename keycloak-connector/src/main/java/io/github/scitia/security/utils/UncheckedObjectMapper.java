package io.github.scitia.security.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class UncheckedObjectMapper extends ObjectMapper {
    final static Logger logger = LoggerFactory.getLogger(UncheckedObjectMapper.class);

    public Map<String, String> map(String body) {
        try {
            return this.readValue(body, new TypeReference<>(){});
        } catch (JsonProcessingException exception) {
            logger.error(exception.getMessage());
        }
        return null;
    }
}
