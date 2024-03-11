package org.complete.challang.app.common.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.complete.challang.app.drink.controller.dto.response.DrinkListFindResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.complete.challang.app.account.user.domain.entity.QDrinkLike.drinkLike;
import static org.complete.challang.app.drink.domain.entity.QDrink.drink;
import static org.complete.challang.app.util.QueryUtils.getReviewRating;

@RequiredArgsConstructor
@Repository
public class MainCustomRepositoryImpl implements MainCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<DrinkListFindResponse> findDrinksByKeyword(final String keyword,
                                                           final Long userId,
                                                           final Pageable pageable) {
        final List<DrinkListFindResponse> drinks = jpaQueryFactory.select(
                        Projections.constructor(
                                DrinkListFindResponse.class,
                                drink.id,
                                drink.imageUrl,
                                drink.drinkManufacturer.manufacturerName,
                                drinkLike.count().eq(1L),
                                drink.name,
                                getReviewRating()
                        )
                )
                .from(drink)
                .leftJoin(drink.drinkLikes, drinkLike)
                .where(
                        Expressions.stringTemplate("replace({0}, ' ', '')", drink.name)
                                .lower()
                                .like("%" + keyword.replace(" ", "").toLowerCase() + "%")
                )
                .orderBy(drink.id.desc())
                .groupBy(drink.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        final JPAQuery<Long> count = jpaQueryFactory.select(drink.id.count())
                .from(drink)
                .where(drink.name.like(keyword));

        return PageableExecutionUtils.getPage(drinks, pageable, count::fetchOne);
    }
}
