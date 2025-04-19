package carservicecrm.models;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class ReviewTest {

    @Test
    public void testReviewFields() {
        Review review = new Review();

        Long id = 1L;
        String reviewText = "Great product!";
        Integer rating = 5;

        review.setId(id);
        review.setReviewText(reviewText);
        review.setRating(rating);

        assertEquals(id, review.getId());
        assertEquals(reviewText, review.getReviewText());
        assertEquals(rating, review.getRating());
    }

    @Test
    public void testReviewUser() {
        Review review = new Review();

        User user = new User();
        review.setUser(user);

        assertEquals(user, review.getUser());
    }

    @Test
    public void testReviewOffers() {
        Review review = new Review();

        Set<Offer> offers = new HashSet<>();
        Offer offer = new Offer();
        offers.add(offer);

        review.setOffers(offers);

        assertEquals(offers, review.getOffers());
        assertTrue(review.getOffers().contains(offer));
    }

    @Test
    public void testReviewEmptyOffers() {
        Review review = new Review();
        Set<Offer> offers = review.getOffers();

        assertNotNull(offers);
        assertTrue(offers.isEmpty());
    }
}