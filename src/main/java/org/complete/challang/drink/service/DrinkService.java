package org.complete.challang.drink.service;

import lombok.RequiredArgsConstructor;
import org.complete.challang.common.exception.ApiException;
import org.complete.challang.common.exception.ErrorCode;
import org.complete.challang.drink.controller.dto.response.DrinkFindResponse;
import org.complete.challang.drink.controller.dto.response.DrinkListFindResponse;
import org.complete.challang.drink.controller.dto.response.DrinkPageResponse;
import org.complete.challang.drink.domain.entity.Drink;
import org.complete.challang.drink.domain.entity.SituationStatistic;
import org.complete.challang.drink.domain.entity.TasteStatistic;
import org.complete.challang.drink.domain.entity.criteria.DrinkSortCriteria;
import org.complete.challang.drink.domain.entity.spec.DrinkSpec;
import org.complete.challang.drink.domain.repository.DrinkRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class DrinkService {

    private final DrinkRepository drinkRepository;

    @Transactional(readOnly = true)
    public DrinkFindResponse findDetailDrink(Long drinkId) {
        Drink drink = findDrink(drinkId);
        DrinkFindResponse drinkFindResponse = drink.toDto();
        drinkFindResponse.updateStatistic(
                drinkRepository.findFoodStatisticById(drinkId),
                drinkRepository.findFlavorStatisticById(drinkId)
        );

        return drinkFindResponse;
    }

    @Transactional(readOnly = true)
    public DrinkPageResponse<DrinkListFindResponse> findRateDrinks(final Long drinkId,
                                                                   final String rate) {
        final Drink drink = findDrink(drinkId);

        // todo: refactor 예정
        String maxField = null;
        if (rate.equals("taste")) { // 맛
            final TasteStatistic tasteStatistic = drink.getTasteStatistic();
            final Field[] tasteFields = TasteStatistic.class.getDeclaredFields();
            maxField = extractMaxRateField(tasteStatistic, tasteFields);
        } else if (rate.equals("situation")) { // 상황
            final SituationStatistic situationStatistic = drink.getSituationStatistic();
            final Field[] situationFields = SituationStatistic.class.getDeclaredFields();
            maxField = extractMaxRateField(situationStatistic, situationFields);
        } else { // 향
            final Optional<Map.Entry<String, Long>> flavorMaxEntry = drink.getReviews().stream()
                    .flatMap(review -> review.getReviewFlavors().stream())
                    .collect(Collectors.groupingBy(reviewFlavor ->
                            reviewFlavor.getFlavor().getFlavor(), Collectors.counting())
                    )
                    .entrySet()
                    .stream()
                    .max(Map.Entry.comparingByValue());

            final String flavorMaxField = flavorMaxEntry.orElseThrow(() -> new ApiException(ErrorCode.FLAVOR_NOT_FOUND)).getKey();

            final Page<DrinkListFindResponse> drinkListFindResponses = drinkRepository.findDrinksOrderByMaxFlavor(
                    flavorMaxField,
                    drinkId,
                    PageRequest.of(0, 4)
            );
            return DrinkPageResponse.toDto(
                    drinkListFindResponses.getContent(),
                    drinkListFindResponses,
                    DrinkSortCriteria.getDrinkSortCriteria(flavorMaxField).getDescription()
            );
        }

        if (maxField == null) throw new ApiException(ErrorCode.DRINK_RATE_PARAM_INCORRECT);

        DrinkSortCriteria drinkSortCriteria = DrinkSortCriteria.getDrinkSortCriteria(maxField);
        final Page<Drink> drinks = drinkRepository.findAll(
                DrinkSpec.orderByRate(drinkSortCriteria, drinkId),
                PageRequest.of(0, 4)
        );
        final List<DrinkListFindResponse> drinkListFindResponses = drinks.getContent().stream().
                map(DrinkListFindResponse::toDto)
                .toList();

        return DrinkPageResponse.toDto(drinkListFindResponses, drinks, drinkSortCriteria.getDescription());
    }

    private Drink findDrink(final Long drinkId) {
        return drinkRepository.findById(drinkId).orElseThrow(() -> new ApiException(ErrorCode.DRINK_NOT_FOUND));
    }

    private <T> String extractMaxRateField(final T statistic,
                                           Field[] fields) {
        Arrays.sort(fields, (o1, o2) -> {
            try {
                o1.setAccessible(true);
                o2.setAccessible(true);
                return Double.compare(o2.getDouble(statistic), o1.getDouble(statistic));
            } catch (IllegalAccessException e) {
                throw new RuntimeException("존재하지 않는 Field입니다");
            }
        });

        return fields[0].getName();
    }
}
