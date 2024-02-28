package org.complete.challang.app.drink.domain.repository;

import org.complete.challang.app.drink.controller.dto.item.FlavorStatisticDto;
import org.complete.challang.app.drink.controller.dto.item.FoodStatisticDto;

import java.util.List;

public interface DrinkQueryRepository {

    List<FoodStatisticDto> findFoodStatisticById(Long drinkId);

    List<FlavorStatisticDto> findFlavorStatisticById(Long drinkId);
}
