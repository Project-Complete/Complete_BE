package org.complete.challang.review.domain.repository;

import org.complete.challang.review.domain.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findAllByIsActiveTrue(Pageable pageable);

    Optional<Review> findByIdAndIsActiveTrue(final Long reviewId);

    Optional<Review> findByIdAndUserIdAndIsActiveTrue(final Long reviewId, final Long userId);
}
