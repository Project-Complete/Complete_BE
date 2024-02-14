package org.complete.challang.review.domain.repository;

import org.complete.challang.review.domain.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
