package com.springboot.price.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalApiService {

    private static final Logger logger = LoggerFactory.getLogger(ExternalApiService.class);

    @Value("${third.party.api.url}")
    private String thirdPartyApiUrl;

    @Value("${third.party.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public ExternalApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getProductsFromThirdParty(String product) {
        String url = thirdPartyApiUrl + "?api_key=" + apiKey + "&product=" + product;
        logger.info("Calling third-party API with URL: {}", url);
        String response = restTemplate.getForObject(url, String.class);
        logger.info("Received response from third-party API for product {}: {}", product, response);
        return response;
    }
}
