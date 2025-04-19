package carservicecrm.services;

import carservicecrm.models.Review;
import carservicecrm.repositories.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceTest {

    @Mock
    private ReviewRepository ReviewRepository;

    @InjectMocks
    private ReviewService ReviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveReviewSuccess() {
        Review Review = new Review();
        Review.setId(1L);
        Review.setReviewText("Sample Review");

        boolean result = ReviewService.saveReview(Review);

        assertTrue(result);
        verify(ReviewRepository, times(1)).save(Review);
    }

    @Test
    void testSaveReviewFailure() {
        Review Review = new Review();

        doThrow(new RuntimeException("Database error")).when(ReviewRepository).save(Review);

        boolean result = ReviewService.saveReview(Review);

        assertFalse(result);
        verify(ReviewRepository, times(1)).save(Review);
    }

    @Test
    void testListReviews() {
        List<Review> expectedReviews = new ArrayList<>();
        Review Review = new Review();
        Review.setId(1L);
        Review.setReviewText("Review 1");
        expectedReviews.add(Review);

        Review Review1 = new Review();
        Review1.setId(1L);
        Review1.setReviewText("Review 1");
        expectedReviews.add(Review1);

        when(ReviewRepository.findAllReviews()).thenReturn(expectedReviews);

        List<Review> actualReviews = ReviewService.list();

        assertEquals(expectedReviews.size(), actualReviews.size());
        assertIterableEquals(expectedReviews, actualReviews);
        verify(ReviewRepository, times(1)).findAllReviews();
    }

    @Test
    void testDeleteReviewExists() {
        Review Review = new Review();
        Review.setId(1L);

        when(ReviewRepository.findReviewById(1L)).thenReturn(Review);

        ReviewService.deleteReview(1L);

        verify(ReviewRepository, times(1)).deleteReviewById(1L);
        verify(ReviewRepository, times(1)).findReviewById(1L);
    }

    @Test
    void testDeleteReviewNotFound() {
        when(ReviewRepository.findReviewById(1L)).thenReturn(null);

        ReviewService.deleteReview(1L);

        verify(ReviewRepository, times(0)).deleteReviewById(1L);
        verify(ReviewRepository, times(1)).findReviewById(1L);
    }

    @Test
    void testGetReview() {
        ReviewService.getReview(1L);
        verify(ReviewRepository, times(1)).findReviewById(1L);
    }
}