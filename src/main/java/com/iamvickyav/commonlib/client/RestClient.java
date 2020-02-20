package com.iamvickyav.commonlib.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class RestClient<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestClient.class);

    @Autowired
    RestTemplate restTemplate;

    public ResponseEntity<T> makeRequest(String url, HttpMethod httpMethod, Object body, Map<String, String> headers, Class<T> responseType) {
        HttpEntity entity = generateHttpEntity(body, headers);
        return restTemplate.exchange(url, httpMethod, entity, responseType);
    }

    public ResponseEntity<List<T>> makeRequestForList(String url, HttpMethod httpMethod, Object body, Map<String, String> headers, ParameterizedTypeReference<List<T>> responseType) {
        HttpEntity entity = generateHttpEntity(body, headers);
        return restTemplate.exchange(url, httpMethod, entity, responseType);
    }

    private HttpEntity generateHttpEntity(Object body, Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach(httpHeaders::add);
        return new HttpEntity(body, httpHeaders);
    }
}
