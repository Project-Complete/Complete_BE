package org.complete.challang.review.repository;

import org.complete.challang.review.domain.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void MembershipRepository가Null이아님() {
        assertThat(reviewRepository).isNotNull();
    }
}
