package org.complete.challang.drink.domain.repository;

import org.complete.challang.drink.controller.dto.response.FlavorStatisticFindResponse;
import org.complete.challang.drink.controller.dto.response.FoodStatisticFindResponse;

import java.util.List;

public interface DrinkQueryRepository {

    List<FoodStatisticFindResponse> findFoodStatisticById(Long drinkId);

    List<FlavorStatisticFindResponse> findFlavorStatisticById(Long drinkId);
}
