package org.complete.challang.app.drink.service;

import lombok.RequiredArgsConstructor;
import org.complete.challang.app.account.oauth2.CustomOAuth2User;
import org.complete.challang.app.account.user.domain.entity.User;
import org.complete.challang.app.account.user.domain.repository.UserRepository;
import org.complete.challang.app.common.exception.ApiException;
import org.complete.challang.app.common.exception.ErrorCode;
import org.complete.challang.app.common.exception.SuccessCode;
import org.complete.challang.app.common.exception.SuccessResponse;
import org.complete.challang.app.drink.controller.dto.request.DrinkCreateRequest;
import org.complete.challang.app.drink.controller.dto.response.*;
import org.complete.challang.app.drink.domain.entity.Package;
import org.complete.challang.app.drink.domain.entity.*;
import org.complete.challang.app.drink.domain.entity.criteria.DrinkSortCriteria;
import org.complete.challang.app.drink.domain.entity.criteria.DrinkTypeCriteria;
import org.complete.challang.app.drink.domain.entity.spec.DrinkSpec;
import org.complete.challang.app.drink.domain.repository.DrinkDetailTypeRepository;
import org.complete.challang.app.drink.domain.repository.DrinkManufacturerRepository;
import org.complete.challang.app.drink.domain.repository.DrinkRepository;
import org.complete.challang.app.drink.domain.repository.PackageRepository;
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
@Transactional(readOnly = true)
@Service
public class DrinkService {

    private final DrinkRepository drinkRepository;
    private final UserRepository userRepository;
    private final DrinkManufacturerRepository drinkManufacturerRepository;
    private final DrinkDetailTypeRepository drinkDetailTypeRepository;
    private final PackageRepository packageRepository;

    public DrinkFindResponse findDetailDrink(final Long drinkId,
                                             final CustomOAuth2User customOAuth2User) {
        final Drink drink = findDrink(drinkId);
        final Long userId = customOAuth2User.getUserId();
        final DrinkFindResponse drinkFindResponse = DrinkFindResponse.toDto(drink);
        drinkFindResponse.updateStatistic(
                drinkRepository.findFoodStatisticById(drinkId),
                drinkRepository.findFlavorStatisticById(drinkId)
        );

        drinkFindResponse.updateDrinkLike(drinkRepository.existByDrinkLike(userId, drinkId));

        return drinkFindResponse;
    }

    public DrinkPageResponse<DrinkListFindResponse> findRateDrinks(final Long drinkId,
                                                                   final String rate,
                                                                   final CustomOAuth2User customOAuth2User) {
        final Drink drink = findDrink(drinkId);
        final Long userId = customOAuth2User.getUserId();

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

        final DrinkSortCriteria drinkSortCriteria = DrinkSortCriteria.getDrinkSortCriteria(maxField);
        final Page<Drink> drinks = drinkRepository.findAll(
                DrinkSpec.orderByRate(drinkSortCriteria, drinkId),
                PageRequest.of(0, 4)
        );
        final List<DrinkListFindResponse> drinkListFindResponses = drinks.getContent().stream()
                .map(e -> DrinkListFindResponse.toDto(e, userId))
                .toList();

        return DrinkPageResponse.toDto(drinkListFindResponses, drinks, drinkSortCriteria.getDescription());
    }

    public DrinkPageResponse<DrinkListFindResponse> findDrinks(final String drinkType,
                                                               final String sorted,
                                                               final int page,
                                                               final CustomOAuth2User customOAuth2User) {
        final DrinkSortCriteria drinkSortCriteria = DrinkSortCriteria.getDrinkSortCriteria(sorted);
        final String type = DrinkTypeCriteria.getPhysicalType(drinkType);
        final Page<DrinkListFindResponse> drinks = drinkRepository.findAllByType(
                type,
                sorted,
                PageRequest.of(page - 1, 8),
                customOAuth2User.getUserId()
        );

        return DrinkPageResponse.toDto(drinks.getContent(), drinks, drinkSortCriteria.getDescription());
    }

    @Transactional
    public SuccessResponse likeDrink(final Long drinkId,
                                     final CustomOAuth2User customOAuth2User) {
        final Drink drink = findDrink(drinkId);
        final Long userId = customOAuth2User.getUserId();
        final User user = userRepository.getReferenceById(userId);

        drink.likeDrink(user);

        return SuccessResponse.toSuccessResponse(SuccessCode.DRINK_LIKE_SUCCESS);
    }

    @Transactional
    public SuccessResponse unLikeDrink(final Long drinkId,
                                       final CustomOAuth2User customOAuth2User) {
        final Drink drink = findDrink(drinkId);
        final Long userId = customOAuth2User.getUserId();
        final User user = userRepository.getReferenceById(userId);

        drink.unLikeDrink(user);

        return SuccessResponse.toSuccessResponse(SuccessCode.DRINK_LIKE_DELETE_SUCCESS);
    }

    public DrinkPageResponse<DrinkBannerListFindResponse> findDrinksForBanner() {
        final Page<DrinkBannerListFindResponse> drinkBannerListFindResponses = drinkRepository.findForBanner();
        final DrinkSortCriteria drinkSortCriteria = DrinkSortCriteria.getDrinkSortCriteria("random");

        return DrinkPageResponse.toDto(drinkBannerListFindResponses.getContent(), drinkBannerListFindResponses, drinkSortCriteria.getDescription());
    }

    @Transactional
    public DrinkCreateResponse createDrink(final DrinkCreateRequest drinkCreateRequest) {
        final Drink drink = drinkCreateRequest.toEntity();

        final DrinkDetailType drinkDetailType = drinkDetailTypeRepository
                .findByDetailType(drinkCreateRequest.getDrinkType().getDetailType())
                .orElseThrow(() -> new ApiException(ErrorCode.DRINK_TYPE_NOT_FOUND));
        final DrinkManufacturer drinkManufacturer = drinkManufacturerRepository
                .findByManufacturerName(drinkCreateRequest.getManufacturer().getManufacturerName())
                .orElseThrow(() -> new ApiException(ErrorCode.DRINK_MANUFACTURER_NOT_FOUND));

        drink.updateDrinkManufacturer(drinkManufacturer);
        drink.updateDrinkDetailType(drinkDetailType);
        drinkCreateRequest.getPackages().forEach(packages -> {
            final Package drinkPackage = packageRepository.findByTypeAndVolume(packages.getType(), packages.getVolume())
                    .orElseThrow(() -> new ApiException(ErrorCode.DRINK_PACKAGE_NOT_FOUND));

            drink.addDrinkPackages(drinkPackage);
        });

        drinkRepository.save(drink);

        return DrinkCreateResponse.toDto(drink);
    }

    private Drink findDrink(final Long drinkId) {
        final Drink drink = drinkRepository.findById(drinkId).orElseThrow(() -> new ApiException(ErrorCode.DRINK_NOT_FOUND));
        if (!drink.isActive()) {
            throw new ApiException(ErrorCode.DRINK_NOT_FOUND);
        }

        return drink;
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
