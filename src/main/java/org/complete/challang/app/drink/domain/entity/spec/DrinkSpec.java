package org.complete.challang.app.drink.domain.entity.spec;

import jakarta.persistence.criteria.Expression;
import org.complete.challang.app.drink.domain.entity.Drink;
import org.complete.challang.app.drink.domain.entity.criteria.DrinkSortCriteria;
import org.springframework.data.jpa.domain.Specification;

public class DrinkSpec {

    public static Specification<Drink> orderByRate(final DrinkSortCriteria drinkSortCriteria,
                                                   final Long drinkId) {
        return (root, query, cb) -> {
            final Expression<Number> avgExpression = cb.quot(
                    root.get(drinkSortCriteria.getEmbeddedValue()).get(drinkSortCriteria.getValue()),
                    root.get("reviewCount")
            );
            return query.where(cb.notEqual(root.get("id"), drinkId)).orderBy(cb.desc(avgExpression)).getRestriction();
        };
    }
}
