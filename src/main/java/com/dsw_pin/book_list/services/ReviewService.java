package com.dsw_pin.book_list.services;

import com.dsw_pin.book_list.dtos.ReviewRecordDto;
import com.dsw_pin.book_list.model.Review;
import com.dsw_pin.book_list.repositories.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<ReviewRecordDto> getReviewDTOsByBookId(UUID bookId) {
        List<Review> reviews = reviewRepository.findByBookId(bookId);
        return reviews.stream()
                .map(review -> new ReviewRecordDto(review.getComment(), review.getUser().getName()))
                .collect(Collectors.toList());
    }


    public List<ReviewRecordDto> getReviewDTOsByUserId(UUID userId) {
        return reviewRepository.findByUserId(userId).stream()
                .map(review -> new ReviewRecordDto(review.getComment(), review.getUser().getName()))
                .collect(Collectors.toList());
    }

}
