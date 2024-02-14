package org.complete.challang.drink.repository;

import org.complete.challang.drink.dto.response.FlavorStatisticFindResponse;
import org.complete.challang.drink.dto.response.FoodStatisticFindResponse;

import java.util.List;

public interface DrinkQueryRepository {

    List<FoodStatisticFindResponse> findFoodStatisticById(Long drinkId);

    List<FlavorStatisticFindResponse> findFlavorStatisticById(Long drinkId);
}
