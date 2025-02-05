package com.example.Library.service;//package com.example.library.service;

import com.example.Library.entity.Rating;
import com.example.Library.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    public Optional<Rating> getRatingById(Long id) {
        return ratingRepository.findById(id);
    }

    public Rating createRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    public Rating updateRating(Long id, Rating updatedRating) {
        return ratingRepository.findById(id).map(rating -> {
            rating.setScore(updatedRating.getScore());
            return ratingRepository.save(rating);
        }).orElseThrow(() -> new RuntimeException("Rating not found"));
    }

    public void deleteRating(Long id) {
        ratingRepository.deleteById(id);
    }
}

