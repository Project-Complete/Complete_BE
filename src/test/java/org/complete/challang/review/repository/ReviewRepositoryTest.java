package org.complete.challang.review.repository;

import org.complete.challang.account.user.domain.entity.User;
import org.complete.challang.drink.domain.entity.Drink;
import org.complete.challang.review.domain.entity.*;
import org.complete.challang.review.domain.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Mock
    private Drink drink;

    @Mock
    private User user;

    @Mock
    private Taste taste;

    @Mock
    private Situation situation;

    @Mock
    private ReviewFlavor reviewFlavor;

    @Mock
    private ReviewFood reviewFood;

    @Test
    public void 주류리뷰등록() {
        // given
        final List<ReviewFlavor> reviewFlavors = new ArrayList<>();
        final List<ReviewFood> reviewFoods = new ArrayList<>();
        reviewFlavors.add(reviewFlavor);
        reviewFoods.add(reviewFood);
        final Review review = Review.builder()
                .imageUrl("image")
                .rating(3)
                .content("content content content content content")
                .situation(situation)
                .taste(taste)
                .drink(drink)
                .user(user)
                .reviewFlavors(reviewFlavors)
                .reviewFoods(reviewFoods)
                .build();

        // when
        final Review result = reviewRepository.save(review);

        // then
        assertThat(result.getImageUrl()).isEqualTo("image");
        assertThat(result.getRating()).isEqualTo(3);
        assertThat(result.getUser()).isEqualTo(user);
        assertThat(result.getSituation()).isEqualTo(situation);
        assertThat(result.getTaste()).isEqualTo(taste);
        assertThat(result.getReviewFlavors()).containsExactlyElementsOf(reviewFlavors);
        assertThat(result.getReviewFoods()).containsExactlyElementsOf(reviewFoods);
    }
}
