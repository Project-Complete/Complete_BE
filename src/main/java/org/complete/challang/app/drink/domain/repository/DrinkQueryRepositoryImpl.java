package org.complete.challang.app.drink.domain.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.complete.challang.app.drink.controller.dto.item.FlavorStatisticDto;
import org.complete.challang.app.drink.controller.dto.item.FoodStatisticDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class DrinkQueryRepositoryImpl implements DrinkQueryRepository {

    private final EntityManager em;

    @Override
    public List<FoodStatisticDto> findFoodStatisticById(final Long drinkId) {
        return em.createQuery(
                        "select new org.complete.challang.app.drink.controller.dto.item.FoodStatisticDto(f.id, f.category, f.imageUrl, count(f.category))"
                                + "from Review r "
                                + "left join r.reviewFoods rf "
                                + "left join rf.food f "
                                + "where r.drink.id = :drinkId "
                                + "group by f.category, f.id "
                                + "order by count(f.category) desc "
                                + "limit 4"
                        , FoodStatisticDto.class
                )
                .setParameter("drinkId", drinkId)
                .getResultList();
    }

    @Override
    public List<FlavorStatisticDto> findFlavorStatisticById(final Long drinkId) {
        return em.createQuery(
                        "select new org.complete.challang.app.drink.controller.dto.item.FlavorStatisticDto(f.id, f.flavor, count(f.flavor))"
                                + "from Review r "
                                + "left join r.reviewFlavors rf "
                                + "left join rf.flavor f "
                                + "where r.drink.id = :drinkId "
                                + "group by f.flavor, f.id "
                                + "order by count(f.flavor) desc "
                                + "limit 3"
                        , FlavorStatisticDto.class
                )
                .setParameter("drinkId", drinkId)
                .getResultList();
    }
}
