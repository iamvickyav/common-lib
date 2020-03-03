package com.iamvickyav.commonlib.client;

import com.iamvickyav.commonlib.exception.BusinessException;
import com.iamvickyav.commonlib.exception.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RestClient<T> {

    private static final Logger logger = LoggerFactory.getLogger(RestClient.class);

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<T> makeRequest(String url, HttpMethod httpMethod, Object body, Map<String, String> headers, Class<T> responseType) {
        return makeRequest(url, httpMethod, body, headers, responseType, new HashMap<>());
    }

    public ResponseEntity<T> makeRequest(String url, HttpMethod httpMethod, Object body, Map<String, String> headers, Class<T> responseType, Map<String, String> urlVariables) {
        long startTime = System.currentTimeMillis();
        logger.info("Initiating Http Call for URL={} with method={}", url, httpMethod);
        ResponseEntity<T> response = null;
        try {
            response = restTemplate.exchange(url, httpMethod, generateHttpEntity(body, headers), responseType, urlVariables);
            long endTime = System.currentTimeMillis();
            logger.info("Http Call completed for URL={} in execTime={}ms with status={}", url, endTime - startTime, response.getStatusCodeValue());
        } catch (HttpClientErrorException exception) {
            logger.info("Exception occured during HTTP Call for URL={} with status={}", url, exception.getRawStatusCode());
            throw new BusinessException(ErrorCode.HTTP_CALL_ERROR);
        }
        return response;
    }

    public ResponseEntity<List<T>> makeRequestForList(String url, HttpMethod httpMethod, Object body, Map<String, String> headers, ParameterizedTypeReference<List<T>> responseType) {
        return makeRequestForList(url, httpMethod, body, headers, responseType, new HashMap<>());
    }

    public ResponseEntity<List<T>> makeRequestForList(String url, HttpMethod httpMethod, Object body, Map<String, String> headers, ParameterizedTypeReference<List<T>> responseType, Map<String, String> uriVariables) {
        long startTime = System.currentTimeMillis();
        logger.info("Initiating Http call for URL={} with method={}", url, httpMethod);
        ResponseEntity<List<T>> response = null;
        try {
            response = restTemplate.exchange(url, httpMethod, generateHttpEntity(body, headers), responseType, uriVariables);
            long endTime = System.currentTimeMillis();
            logger.info("Http call completed for URL={} in execTime={}ms with status={}", url, endTime - startTime, response.getStatusCodeValue());
        } catch (HttpClientErrorException exception) {
            logger.info("Exception occured during HTTP Call for URL={} with status={}", url, exception.getRawStatusCode());
            throw new BusinessException(ErrorCode.HTTP_CALL_ERROR);
        }
        return response;
    }

    private HttpEntity<Object> generateHttpEntity(Object body, Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach(httpHeaders::add);
        return new HttpEntity<>(body, httpHeaders);
    }
}
