package com.springboot.price.controller;

import com.springboot.price.service.ExternalApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = { "http://localhost:5173"})
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ExternalApiService externalApiService;

    @Autowired
    public ProductController(ExternalApiService externalApiService) {
        this.externalApiService = externalApiService;
    }

    @GetMapping("/{product}")
    public ResponseEntity<String> getProducts(@PathVariable("product") String product) {
        logger.info("Received request to get products for: {}", product);
        String response = externalApiService.getProductsFromThirdParty(product);
        logger.info("Successfully retrieved products for: {}", product);
        return ResponseEntity.ok(response);
    }
}
