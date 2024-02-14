package org.complete.challang.drink.service;

import lombok.RequiredArgsConstructor;
import org.complete.challang.common.exception.ApiException;
import org.complete.challang.common.exception.ErrorCode;
import org.complete.challang.drink.domain.entity.Drink;
import org.complete.challang.drink.controller.dto.response.DrinkFindResponse;
import org.complete.challang.drink.domain.repository.DrinkRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DrinkService {

    private final DrinkRepository drinkRepository;

    public DrinkFindResponse findDetailDrink(Long drinkId) {
        Drink drink = drinkRepository.findById(drinkId).orElseThrow(() -> new ApiException(ErrorCode.DRINK_NOT_FOUND));
        DrinkFindResponse drinkFindResponse = drink.toDto();
        drinkFindResponse.updateStatistic(drinkRepository.findFoodStatisticById(drinkId), drinkRepository.findFlavorStatisticById(drinkId));

        return drinkFindResponse;
    }
}
