package com.tiwilli.cryptoport.util;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class Utils {

    public static HttpHeaders BuildHeaders(String headerName, String headerValue, MediaType mediaType) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(headerName, headerValue);
        headers.setContentType(mediaType);
        return headers;
    }

    public static <T> ResponseEntity<T> makeApiCall(String url, HttpEntity<String> entity, Class<T> responseType) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                responseType
        );
    }


}
