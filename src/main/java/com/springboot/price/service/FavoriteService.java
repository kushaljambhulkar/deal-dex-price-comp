package com.springboot.price.service;

import com.springboot.price.entity.Favorite;
import com.springboot.price.entity.User;
import com.springboot.price.repository.FavoriteRepository;
import com.springboot.price.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class FavoriteService {

    private static final Logger logger = LoggerFactory.getLogger(FavoriteService.class);

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    public Favorite addFavorite(String usernameOrEmail,  Favorite favorite) {
        logger.info("Adding favorite for user: {}", usernameOrEmail);

        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email: "+ usernameOrEmail));

        favorite.setUser(user);
        Favorite savedFavorite = favoriteRepository.save(favorite);

        logger.info("Favorite added successfully for user: {}", usernameOrEmail);
        return savedFavorite;
    }

    public List<Favorite> getFavorites(String usernameOrEmail) {
        logger.info("Fetching favorites for user: {}", usernameOrEmail);

        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email: "+ usernameOrEmail));

        List<Favorite> favorites = favoriteRepository.findByUserId(user.getId());

        logger.info("Fetched {} favorites for user: {}", favorites.size(), usernameOrEmail);
        return favorites;
    }

    public void clearFavorites(Long userId) {
        logger.info("Clearing favorites for user with ID: {}", userId);

        favoriteRepository.deleteByUserId(userId);

        logger.info("Favorites cleared successfully for user with ID: {}", userId);
    }

    // Fetch products from third-party API
    public Object fetchProductsFromExternalApi(String query) {
        logger.info("Fetching products from third-party API for query: {}", query);

        String apiKey = "49zak8PDdr7nD5R8X6nEenzTDOrX8FY7Iqg";
        String url = String.format("https://price-api.datayuge.com/api/v1/compare/search?api_key=%s&product=%s", apiKey, query);
        Object response = restTemplate.getForObject(url, Object.class);

        logger.info("Received response from third-party API for query {}: {}", query, response);
        return response;
    }
}
