package org.complete.challang.review.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.complete.challang.account.user.domain.entity.User;
import org.complete.challang.common.dto.PageInfoDto;
import org.complete.challang.common.exception.ApiException;
import org.complete.challang.drink.domain.entity.Drink;
import org.complete.challang.drink.domain.repository.DrinkRepository;
import org.complete.challang.drink.domain.repository.FoodRepository;
import org.complete.challang.review.controller.dto.item.ReviewDto;
import org.complete.challang.review.controller.dto.item.SituationDto;
import org.complete.challang.review.controller.dto.item.TasteDto;
import org.complete.challang.review.controller.dto.request.ReviewCreateRequest;
import org.complete.challang.review.controller.dto.response.ReviewCreateResponse;
import org.complete.challang.review.controller.dto.response.ReviewListFindResponse;
import org.complete.challang.review.domain.entity.Review;
import org.complete.challang.review.domain.entity.ReviewFlavor;
import org.complete.challang.review.domain.entity.ReviewFood;
import org.complete.challang.review.domain.entity.ReviewSortCriteria;
import org.complete.challang.review.domain.repository.FlavorRepository;
import org.complete.challang.review.domain.repository.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.complete.challang.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final int REVIEW_LIST_SIZE = 6;

    private final DrinkRepository drinkRepository;
    private final FlavorRepository flavorRepository;
    private final FoodRepository foodRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public ReviewCreateResponse createReview(final ReviewCreateRequest reviewCreateRequest) {
        // 사용자 검증 로직 필요
        final User user = null;
        final Drink drink = drinkRepository.findById(reviewCreateRequest.getDrinkId())
                .orElseThrow(() -> new ApiException(DRINK_NOT_FOUND));

        final Drink savedDrink = updateDrinkReviewSum(drink,
                reviewCreateRequest.getRating(),
                reviewCreateRequest.getSituationDto(),
                reviewCreateRequest.getTasteDto());

        final List<ReviewFlavor> reviewFlavors = createReviewFlavors(reviewCreateRequest.getFlavors());
        final List<ReviewFood> reviewFoods = createReviewFoods(reviewCreateRequest.getFoods());
        final Review review = reviewCreateRequest.toEntity(savedDrink, user, reviewFlavors, reviewFoods);
        final Review savedReview = reviewRepository.save(review);

        return ReviewCreateResponse.toDto(savedReview);
    }

    @Transactional
    public ReviewListFindResponse findReviewList(final int page, final String sort) {
        PageRequest pageRequest = PageRequest.of(page, REVIEW_LIST_SIZE, ReviewSortCriteria.sortCriteriaOfValue(sort));
        Page<Review> reviews = reviewRepository.findAll(pageRequest);

        List<ReviewDto> reviewDtos = reviews.stream()
                .map(review -> ReviewDto.toDto(review))
                .collect(Collectors.toList());

        return ReviewListFindResponse.builder()
                .reviews(reviewDtos)
                .pageInfo(PageInfoDto.toDto(page,
                        REVIEW_LIST_SIZE,
                        reviews.getTotalElements(),
                        sort))
                .build();
    }

    private Drink updateDrinkReviewSum(final Drink drink,
                                       final float rating,
                                       final SituationDto situationDto,
                                       final TasteDto tasteDto) {
        drink.updateReviewCount();
        drink.updateReviewSumRating(rating);
        updateDrinkSituationStatistic(drink, situationDto);
        updateDrinkTasteStatistic(drink, tasteDto);
        return drinkRepository.save(drink);
    }

    private List<ReviewFlavor> createReviewFlavors(final List<Long> flavors) {
        return flavors.stream()
                .map(flavorId -> ReviewFlavor.builder()
                        .flavor(flavorRepository.findById(flavorId)
                                .orElseThrow(() -> new ApiException(FLAVOR_NOT_FOUND)))
                        .build()
                ).collect(Collectors.toList());
    }

    private List<ReviewFood> createReviewFoods(final List<Long> foods) {
        return foods.stream()
                .map(foodId -> ReviewFood.builder()
                        .food(foodRepository.findById(foodId)
                                .orElseThrow(() -> new ApiException(FOOD_NOT_FOUND)))
                        .build()
                ).collect(Collectors.toList());
    }

    private void updateDrinkTasteStatistic(final Drink drink, final TasteDto tasteDto) {
        drink.getTasteStatistic()
                .updateTasteRating(tasteDto.getSweet(),
                        tasteDto.getSour(),
                        tasteDto.getBitter(),
                        tasteDto.getBody(),
                        tasteDto.getRefresh());
    }

    private void updateDrinkSituationStatistic(final Drink drink, final SituationDto situationDto) {
        drink.getSituationStatistic()
                .updateSituationStatistic(situationDto.isAdult(),
                        situationDto.isPartner(),
                        situationDto.isFriend(),
                        situationDto.isBusiness(),
                        situationDto.isAlone());
    }
}
