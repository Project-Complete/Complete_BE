package org.complete.challang.drink.domain.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.complete.challang.drink.controller.dto.response.DrinkListFindResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.complete.challang.drink.domain.entity.QDrink.drink;
import static org.complete.challang.drink.domain.entity.QDrinkType.drinkType1;

@RequiredArgsConstructor
@Repository
public class DrinkCustomRepositoryImpl implements DrinkCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<DrinkListFindResponse> findAllByType(final String type,
                                                     final String sorted,
                                                     final Pageable pageable) {
        List<DrinkListFindResponse> drinks = jpaQueryFactory.select(Projections.constructor(
                        DrinkListFindResponse.class,
                        drink.id,
                        drink.imageUrl,
                        drink.drinkManufacturer.manufacturerName,
                        drink.name,
                        drink.reviewSumRating.divide(drink.reviewCount)
                ))
                .from(drink)
                .join(drink.drinkDetailType.drinkType, drinkType1)//.fetchJoin()
                .where(whereType(type))
                .orderBy(orderBySort(sorted))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = jpaQueryFactory.select(drink.count())
                .where(whereType(type));

        return PageableExecutionUtils.getPage(drinks, pageable, count::fetchOne);
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
}
