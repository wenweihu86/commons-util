package com.github.wenweihu86.commmons.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.text.SimpleDateFormat;

@SuppressWarnings(value={"unchecked"})
public class JsonUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    public static JsonNode readTree(String jsonString) {
        JsonNode node = null;
        try {
            node = objectMapper.readTree(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return node;
    }

    public static <T> String toJson(T object) {
        String s = "";
        try {
            s = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static String toJson(JsonNode node) {
        String s = "";
        try {
            s = objectMapper.writeValueAsString(node);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static <T> T fromJson(String jsonString, TypeReference<T> tr) {
        try {
            return (T) objectMapper.readValue(jsonString, tr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T fromJson(String jsonString, Class<T> classOfT) {
        try {
            return (T) objectMapper.readValue(jsonString, classOfT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
