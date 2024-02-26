package org.complete.challang.drink.service;

import lombok.RequiredArgsConstructor;
import org.complete.challang.account.user.domain.entity.User;
import org.complete.challang.account.user.domain.repository.UserRepository;
import org.complete.challang.common.exception.ApiException;
import org.complete.challang.common.exception.ErrorCode;
import org.complete.challang.common.exception.SuccessCode;
import org.complete.challang.common.exception.SuccessResponse;
import org.complete.challang.drink.controller.dto.response.DrinkFindResponse;
import org.complete.challang.drink.controller.dto.response.DrinkListFindResponse;
import org.complete.challang.drink.controller.dto.response.DrinkPageResponse;
import org.complete.challang.drink.domain.entity.Drink;
import org.complete.challang.drink.domain.entity.SituationStatistic;
import org.complete.challang.drink.domain.entity.TasteStatistic;
import org.complete.challang.drink.domain.entity.criteria.DrinkSortCriteria;
import org.complete.challang.drink.domain.entity.criteria.DrinkTypeCriteria;
import org.complete.challang.drink.domain.entity.spec.DrinkSpec;
import org.complete.challang.drink.domain.repository.DrinkRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public DrinkFindResponse findDetailDrink(final Long drinkId,
                                             final UserDetails userDetails) {
        final Drink drink = findDrink(drinkId);
        final DrinkFindResponse drinkFindResponse = drink.toDto();
        drinkFindResponse.updateStatistic(
                drinkRepository.findFoodStatisticById(drinkId),
                drinkRepository.findFlavorStatisticById(drinkId)
        );

        Long userId = userDetails != null ? Long.valueOf(userDetails.getUsername()) : 0L;
        drinkFindResponse.updateDrinkLike(drinkRepository.existByDrinkLike(userId, drinkId));

        return drinkFindResponse;
    }

    @Transactional(readOnly = true)
    public DrinkPageResponse<DrinkListFindResponse> findRateDrinks(final Long drinkId,
                                                                   final String rate,
                                                                   final UserDetails userDetails) {
        final Drink drink = findDrink(drinkId);
        Long userId = userDetails != null ? Long.valueOf(userDetails.getUsername()) : 0L;

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
                    PageRequest.of(0, 4),
                    userId
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
        final List<DrinkListFindResponse> drinkListFindResponses = drinks.getContent().stream()
                .map(e -> DrinkListFindResponse.toDto(e, userId))
                .toList();

        return DrinkPageResponse.toDto(drinkListFindResponses, drinks, drinkSortCriteria.getDescription());
    }

    @Transactional(readOnly = true)
    public DrinkPageResponse<DrinkListFindResponse> findDrinks(final String drinkType,
                                                               final String sorted,
                                                               final int page,
                                                               final UserDetails userDetails) {
        final DrinkSortCriteria drinkSortCriteria = DrinkSortCriteria.getDrinkSortCriteria(sorted);
        final String type = DrinkTypeCriteria.getPhysicalType(drinkType);
        final Page<DrinkListFindResponse> drinks = drinkRepository.findAllByType(
                type,
                sorted,
                PageRequest.of(page - 1, 8),
                userDetails != null ? Long.valueOf(userDetails.getUsername()) : 0L
        );

        return DrinkPageResponse.toDto(drinks.getContent(), drinks, drinkSortCriteria.getDescription());
    }

    public SuccessResponse likeDrink(final Long drinkId,
                                     final UserDetails userDetails) {
        Drink drink = findDrink(drinkId);
        Long userId = Long.valueOf(userDetails.getUsername());
        User user = userRepository.getReferenceById(userId);

        drink.likeDrink(user);

        return SuccessResponse.toSuccessResponse(SuccessCode.DRINK_LIKE_SUCCESS);
    }

    public SuccessResponse unLikeDrink(final Long drinkId,
                                       final UserDetails userDetails) {
        Drink drink = findDrink(drinkId);
        Long userId = Long.valueOf(userDetails.getUsername());
        User user = userRepository.getReferenceById(userId);

        drink.unLikeDrink(user);

        return SuccessResponse.toSuccessResponse(SuccessCode.DRINK_LIKE_DELETE_SUCCESS);
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
