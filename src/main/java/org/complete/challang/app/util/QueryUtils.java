package org.complete.challang.app.util;

import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static org.complete.challang.app.drink.domain.entity.QDrink.drink;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QueryUtils {

    public static NumberExpression<Double> getReviewRating() {
        return new CaseBuilder()
                .when(drink.reviewCount.eq(0L)).then(0.0)
                .otherwise(drink.reviewSumRating.divide(drink.reviewCount));
    }
}
