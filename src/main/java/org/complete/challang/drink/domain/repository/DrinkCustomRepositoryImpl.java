package org.complete.challang.drink.domain.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.complete.challang.drink.controller.dto.response.DrinkBannerListFindResponse;
import org.complete.challang.drink.controller.dto.response.DrinkListFindResponse;
import org.complete.challang.drink.controller.dto.response.FoodStatisticFindResponse;
import org.complete.challang.drink.controller.dto.response.ManufacturerFindResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static org.complete.challang.account.user.domain.entity.QDrinkLike.drinkLike;
import static org.complete.challang.drink.domain.entity.QDrink.drink;
import static org.complete.challang.drink.domain.entity.QDrinkManufacturer.drinkManufacturer;
import static org.complete.challang.drink.domain.entity.QDrinkType.drinkType1;
import static org.complete.challang.drink.domain.entity.QFood.food;
import static org.complete.challang.drink.domain.entity.QLocation.location1;
import static org.complete.challang.review.domain.entity.QReview.review;
import static org.complete.challang.review.domain.entity.QReviewFood.reviewFood;

@RequiredArgsConstructor
@Repository
public class DrinkCustomRepositoryImpl implements DrinkCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<DrinkListFindResponse> findAllByType(final String type,
                                                     final String sorted,
                                                     final Pageable pageable,
                                                     final Long userId) {
        List<DrinkListFindResponse> drinks = jpaQueryFactory.select(Projections.constructor(
                        DrinkListFindResponse.class,
                        drink.id,
                        drink.imageUrl,
                        drink.drinkManufacturer.manufacturerName,
                        drinkLike.count().eq(1L),
                        drink.name,
                        drink.reviewSumRating.divide(drink.reviewCount)
                ))
                .from(drink)
                .join(drink.drinkDetailType.drinkType, drinkType1)//.fetchJoin()
                .leftJoin(drink.drinkLikes, drinkLike).on(drinkLike.user.id.eq(userId))
                .where(whereType(type))
                .orderBy(orderBySort(sorted))
                .groupBy(drink.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = jpaQueryFactory.select(drink.count())
                .where(whereType(type));

        return PageableExecutionUtils.getPage(drinks, pageable, count::fetchOne);
    }

    @Override
    public Page<DrinkBannerListFindResponse> findForBanner() {
        Set<Long> randomIds = randomDrinkIds();

        List<DrinkBannerListFindResponse> drinkBannerListFindResponses = jpaQueryFactory
                .from(drink)
                .join(drink.drinkManufacturer, drinkManufacturer)
                .join(drinkManufacturer.location, location1)
                .join(drink.reviews, review)
                .join(review.reviewFoods, reviewFood)
                .leftJoin(reviewFood.food, food)
                .where(drink.id.in(randomIds))
                .groupBy(drink.id, food.id)
                .orderBy(drink.id.asc(), food.id.count().desc())
                .transform(
                        groupBy(drink.id).list(
                                Projections.constructor(
                                        DrinkBannerListFindResponse.class,
                                        drink.id,
                                        drink.name,
                                        drink.imageUrl,
                                        Projections.constructor(
                                                ManufacturerFindResponse.class,
                                                drinkManufacturer.id,
                                                drinkManufacturer.manufacturerName,
                                                location1.location
                                        ),
                                        drink.description,
                                        list(
                                                Projections.constructor(
                                                        FoodStatisticFindResponse.class,
                                                        food.id,
                                                        food.category,
                                                        food.imageUrl,
                                                        food.id.count()
                                                )
                                        ),
                                        drink.abv
                                )
                        )
                );

        return PageableExecutionUtils.getPage(drinkBannerListFindResponses, PageRequest.of(0, 4), drinkBannerListFindResponses::size);
    }

    private BooleanExpression whereType(String type) {
        if (!type.equals("전체")) {
            return drinkType1.drinkType.eq(type);
        }

        return null;
    }

    private OrderSpecifier<?> orderBySort(String sorted) {
        if (sorted.equals("latest_order")) {
            return drink.createdDate.desc();
        } else if (sorted.equals("popularity_order")) {
            return drink.drinkLikes.size().desc();
        }
        return null;
    }

    private Set<Long> randomDrinkIds() {
        Long totalElement = jpaQueryFactory.select(drink.count())
                .from(drink)
                .fetchOne();

        Random random = new Random();
        Set<Long> randIdSet = new HashSet<>();
        int count = 20;
        while (randIdSet.size() < 4 && count > 0) {
            long randId = random.nextLong(totalElement) + 1;
            Long isActive = jpaQueryFactory.select(drink.count())
                    .from(drink)
                    .where(drink.id.eq(randId))
                    .where(drink.isActive.eq(true))
                    .fetchOne();

            if (isActive.equals(1L)) {
                randIdSet.add(randId);
            }

            count--;
        }

        return randIdSet;
    }
}
