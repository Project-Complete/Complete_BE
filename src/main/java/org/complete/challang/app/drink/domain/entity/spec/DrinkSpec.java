package org.complete.challang.app.drink.domain.entity.spec;

import jakarta.persistence.criteria.Expression;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.complete.challang.app.drink.domain.entity.Drink;
import org.complete.challang.app.drink.domain.entity.criteria.DrinkSortCriteria;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DrinkSpec {

    public static Specification<Drink> orderByRate(final DrinkSortCriteria drinkSortCriteria,
                                                   final Long drinkId) {
        return (root, query, cb) -> {
            final Expression<Number> avgExpression = cb.quot(
                    root.get(drinkSortCriteria.getEmbeddedValue()).get(drinkSortCriteria.getValue()),
                    cb.<Number>selectCase()
                            .when(cb.equal(root.get("reviewCount"), 0), cb.literal(1))
                            .otherwise(root.get("reviewCount"))
            );

            return query
                    .where(
                            cb.and(
                                    cb.notEqual(root.get("id"), drinkId),
                                    cb.isTrue(root.get("isActive"))
                            )
                    )
                    .orderBy(cb.desc(avgExpression))
                    .getRestriction();
        };
    }
}
