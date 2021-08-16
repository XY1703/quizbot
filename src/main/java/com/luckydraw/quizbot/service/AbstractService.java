package com.luckydraw.quizbot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

public abstract class AbstractService<T> {

    protected String url;
    protected Class<T> clazz;
    private static ObjectMapper mapper = new ObjectMapper();

    public AbstractService() {
    }

    public List<T> getAll(String param){
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        List<T> list = null;
        try {
            list = mapper.readValue(makeRequest(param), TypeFactory.defaultInstance()
                    .constructCollectionType(List.class, clazz));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return list;
    }

    public T getInstance(String arg){
        T t = null;
        try {
            t = mapper.readValue(makeRequest(url + arg), clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return t;
    }

    protected String makeRequest(String url) {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()))){
            String inputLine;

            while ((inputLine = reader.readLine()) != null)
                sb.append(inputLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
