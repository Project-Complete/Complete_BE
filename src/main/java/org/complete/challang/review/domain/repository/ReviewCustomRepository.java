package org.complete.challang.review.domain.repository;

import org.complete.challang.review.domain.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewCustomRepository {

    Page<Review> findAllWithOption(final Pageable pageable,
                                   final Long drinkId,
                                   final Long writerId);
}
