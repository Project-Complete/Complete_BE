package org.complete.challang.review.domain.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.complete.challang.review.domain.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.complete.challang.review.domain.entity.QReview.review;

@Repository
public class ReviewCustomRepositoryImpl extends QuerydslRepositorySupport implements ReviewCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public ReviewCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Review.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<Review> findAllWithOption(final Pageable pageable,
                                          final Long drinkId,
                                          final Long writerId) {
        JPQLQuery<Review> query = jpaQueryFactory.selectFrom(review)
                .where(review.isActive.eq(true), eqDrinkId(drinkId), eqWriterId(writerId));
        List<Review> reviews = this.getQuerydsl().applyPagination(pageable, query).fetch();
        
        return new PageImpl<Review>(reviews, pageable, query.fetchCount());
    }

    private BooleanExpression eqDrinkId(Long drinkId) {
        if (drinkId == null) {
            return null;
        }
        return review.drink.id.eq(drinkId);
    }

    private BooleanExpression eqWriterId(Long writerId) {
        if (writerId == null) {
            return null;
        }
        return review.user.id.eq(writerId);
    }
}
