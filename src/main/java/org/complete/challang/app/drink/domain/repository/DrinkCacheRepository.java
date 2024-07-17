package org.complete.challang.app.drink.domain.repository;

import lombok.RequiredArgsConstructor;
import org.complete.challang.app.drink.controller.dto.response.DrinkFindResponse;
import org.complete.challang.app.drink.domain.entity.Drink;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DrinkCacheRepository {

    private final DrinkRepository drinkRepository;

    @Cacheable(value = "readDrink", key = "#drink.id", unless = "#drink.id == null")
    public DrinkFindResponse toDrinkFindResponse(final Drink drink) {
        final DrinkFindResponse drinkFindResponse = DrinkFindResponse.toDto(drink);
        drinkFindResponse.updateStatistic(
                drinkRepository.findFoodStatisticById(drink.getId()),
                drinkRepository.findFlavorStatisticById(drink.getId())
        );

        return drinkFindResponse;
    }


}
