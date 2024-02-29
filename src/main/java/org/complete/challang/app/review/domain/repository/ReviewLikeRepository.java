package org.complete.challang.app.review.domain.repository;

import org.complete.challang.app.account.user.domain.entity.User;
import org.complete.challang.app.review.domain.entity.Review;
import org.complete.challang.app.review.domain.entity.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {

    boolean existsByUserAndReview(final User user, final Review review);

    void deleteByUserAndReview(final User user, final Review review);
}
