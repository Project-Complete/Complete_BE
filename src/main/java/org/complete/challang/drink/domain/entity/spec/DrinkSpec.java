package org.complete.challang.drink.domain.entity.spec;

import jakarta.persistence.criteria.Expression;
import org.complete.challang.drink.domain.entity.Drink;
import org.complete.challang.drink.domain.entity.criteria.DrinkSortCriteria;
import org.springframework.data.jpa.domain.Specification;

public class DrinkSpec {

    public static Specification<Drink> orderByRate(final DrinkSortCriteria drinkSortCriteria) {
        return (root, query, cb) -> {
            final Expression<Number> avgExpression = cb.quot(root.get(drinkSortCriteria.getEmbeddedValue()).get(drinkSortCriteria.getValue()), root.get("reviewCount"));
            return query.orderBy(cb.desc(avgExpression)).getRestriction();
        };
    }
}
