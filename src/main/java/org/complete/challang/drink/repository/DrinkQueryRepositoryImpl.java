package org.complete.challang.drink.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.complete.challang.drink.dto.response.FlavorStatisticFindResponse;
import org.complete.challang.drink.dto.response.FoodStatisticFindResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class DrinkQueryRepositoryImpl implements DrinkQueryRepository {

    private final EntityManager em;

    @Override
    public List<FoodStatisticFindResponse> findFoodStatisticById(Long drinkId) {
        return em.createQuery(
                        "select new org.complete.challang.drink.dto.response.FoodStatisticFindResponse(f.id, f.category, f.imageUrl, count(f.category))"
                                + "from Review r "
                                + "left join r.reviewFoods rf "
                                + "left join rf.food f "
                                + "where r.drink.id = :drinkId "
                                + "group by f.category "
                                + "order by count(f.category) desc "
                                + "limit 4"
                        , FoodStatisticFindResponse.class
                )
                .setParameter("drinkId", drinkId)
                .getResultList();
    }

    @Override
    public List<FlavorStatisticFindResponse> findFlavorStatisticById(Long drinkId) {
        return em.createQuery(
                        "select new org.complete.challang.drink.dto.response.FlavorStatisticFindResponse(f.id, f.flavor, count(f.flavor))"
                                + "from Review r "
                                + "left join r.reviewFlavors rf "
                                + "left join rf.flavor f "
                                + "where r.drink.id = :drinkId "
                                + "group by f.flavor "
                                + "order by count(f.flavor) desc "
                                + "limit 3"
                        , FlavorStatisticFindResponse.class
                )
                .setParameter("drinkId", drinkId)
                .getResultList();
    }
}
