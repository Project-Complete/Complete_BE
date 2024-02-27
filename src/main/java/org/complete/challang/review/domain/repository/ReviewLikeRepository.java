package org.complete.challang.review.domain.repository;

import org.complete.challang.account.user.domain.entity.User;
import org.complete.challang.review.domain.entity.Review;
import org.complete.challang.review.domain.entity.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {

    boolean existsByUserAndReview(final User user, final Review review);
}
