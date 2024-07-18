package com.springboot.price.controller;

import com.springboot.price.entity.Favorite;
import com.springboot.price.service.FavoriteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = { "http://localhost:5173"})
@RequestMapping("/api/favorites")
public class FavoriteController {

    private static final Logger logger = LoggerFactory.getLogger(FavoriteController.class);

    @Autowired
    private FavoriteService favoriteService;

    @PostMapping("/{userId}/{productId}")
    public ResponseEntity<Favorite> addFavorite(@PathVariable String userId, String productId,@RequestBody Favorite favorite) {
        logger.info("Received request to add favorite for user: {}, product: {}", userId, productId);
        Favorite result = favoriteService.addFavorite(userId, favorite);
        logger.info("Successfully added favorite for user: {}, product: {}", userId, favorite.getProductId());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Favorite>> getFavorites(@PathVariable String userId) {
        logger.info("Received request to get favorites for user: {}", userId);
        List<Favorite> favorites = favoriteService.getFavorites(userId);
        logger.info("Successfully retrieved favorites for user: {}", userId);
        return ResponseEntity.ok(favorites);
    }
}




































//    @DeleteMapping("/{userId}/clear")
//    public ResponseEntity<String> clearFavorites(@PathVariable Long userId) {
//        logger.info("Received request to clear favorites for user: {}", userId);
//        favoriteService.clearFavorites(userId);
//        logger.info("Successfully cleared favorites for user: {}", userId);
//        return ResponseEntity.ok("Favorites cleared successfully.");
//    }


