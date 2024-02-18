package org.complete.challang.review.service;

import lombok.RequiredArgsConstructor;
import org.complete.challang.account.user.domain.entity.User;
import org.complete.challang.account.user.domain.repository.UserRepository;
import org.complete.challang.common.dto.PageInfoDto;
import org.complete.challang.common.exception.ApiException;
import org.complete.challang.common.exception.SuccessCode;
import org.complete.challang.drink.domain.entity.Drink;
import org.complete.challang.drink.domain.entity.SituationStatistic;
import org.complete.challang.drink.domain.entity.TasteStatistic;
import org.complete.challang.drink.domain.repository.DrinkRepository;
import org.complete.challang.drink.domain.repository.FoodRepository;
import org.complete.challang.review.controller.dto.item.ReviewDto;
import org.complete.challang.review.controller.dto.item.SituationDto;
import org.complete.challang.review.controller.dto.item.TasteDto;
import org.complete.challang.review.controller.dto.request.ReviewCreateRequest;
import org.complete.challang.review.controller.dto.response.ReviewCreateResponse;
import org.complete.challang.review.controller.dto.response.ReviewDetailResponse;
import org.complete.challang.review.controller.dto.response.ReviewListFindResponse;
import org.complete.challang.review.domain.entity.*;
import org.complete.challang.review.domain.repository.FlavorRepository;
import org.complete.challang.review.domain.repository.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.complete.challang.common.exception.ErrorCode.*;


@RequiredArgsConstructor
@Service
public class ReviewService {

    private final int REVIEW_LIST_SIZE = 6;

    private final DrinkRepository drinkRepository;
    private final FlavorRepository flavorRepository;
    private final FoodRepository foodRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @Transactional
    public ReviewCreateResponse createReview(final ReviewCreateRequest reviewCreateRequest,
                                             final Long userId) {
        final User user = userRepository.findByIdAndIsActiveTrue(userId)
                .orElseThrow(() -> new ApiException(USER_NOT_FOUND));
        final Drink drink = drinkRepository.findByIdAndIsActiveTrue(reviewCreateRequest.getDrinkId())
                .orElseThrow(() -> new ApiException(DRINK_NOT_FOUND));

        final Drink savedDrink = updateDrink(drink,
                reviewCreateRequest.getRating(),
                reviewCreateRequest.getSituationDto(),
                reviewCreateRequest.getTasteDto());

        final List<ReviewFlavor> reviewFlavors = createReviewFlavors(reviewCreateRequest.getFlavors());
        final List<ReviewFood> reviewFoods = createReviewFoods(reviewCreateRequest.getFoods());
        final Review review = reviewCreateRequest.toEntity(savedDrink, user, reviewFlavors, reviewFoods);
        final Review savedReview = reviewRepository.save(review);

        return ReviewCreateResponse.toDto(savedReview);
    }

    @Transactional(readOnly = true)
    public ReviewListFindResponse findReviewList(final Long drinkId,
                                                 final int page,
                                                 final String sort) {
        final PageRequest pageRequest = PageRequest.of(page, REVIEW_LIST_SIZE, ReviewSortCriteria.sortCriteriaOfValue(sort));

        Page<Review> reviews;
        if (drinkId == null) {
            reviews = reviewRepository.findAllByIsActiveTrue(pageRequest);
        } else {
            reviews = reviewRepository.findAllByDrinkIdAndIsActiveTrue(drinkId, pageRequest);
        }

        final List<ReviewDto> reviewDtos = reviews.stream()
                .map(review -> ReviewDto.toDto(review))
                .collect(toList());

        return ReviewListFindResponse.toDto(reviewDtos,
                PageInfoDto.toDto(page,
                        REVIEW_LIST_SIZE,
                        reviews.getTotalElements(),
                        sort));
    }

    @Transactional(readOnly = true)
    public ReviewDetailResponse findReviewDetail(final Long reviewId) {
        final Review review = reviewRepository.findByIdAndIsActiveTrue(reviewId)
                .orElseThrow(() -> new ApiException(REVIEW_NOT_FOUND));

        return ReviewDetailResponse.toDto(review,
                review.getReviewFoods()
                        .stream()
                        .map(reviewFood -> reviewFood.getFood().getCategory())
                        .collect(toList()),
                review.getReviewFlavors()
                        .stream()
                        .map(reviewFlavor -> reviewFlavor.getFlavor().getFlavor())
                        .collect(toList()));
    }

    @Transactional
    public SuccessCode deleteReview(final Long userId,
                                    final Long reviewId) {
        final Review review = reviewRepository.findByIdAndUserIdAndIsActiveTrue(reviewId, userId)
                .orElseThrow(() -> new ApiException(REVIEW_REMOVE_FORBIDDEN));
        final Drink drink = review.getDrink();

        downgradeDrink(drink,
                review.getRating(),
                review.getSituation(),
                review.getTaste());
        review.deleteReview();

        return SuccessCode.REVIEW_DELETE_SUCCESS;
    }

    private Drink updateDrink(final Drink drink,
                              final float rating,
                              final SituationDto situationDto,
                              final TasteDto tasteDto) {
        drink.updateReviewCount();
        drink.updateReviewSumRating(rating);
        updateDrinkSituationStatistic(drink.getSituationStatistic(), situationDto);
        updateDrinkTasteStatistic(drink.getTasteStatistic(), tasteDto);

        return drink;
    }

    private void downgradeDrink(final Drink drink,
                                final float rating,
                                final Situation situation,
                                final Taste taste) {
        drink.downgradeReviewCount();
        drink.downgradeReviewSumRating(rating);
        downgradeDrinkSituationStatistic(drink.getSituationStatistic(), situation);
        downgradeDrinkTasteStatistic(drink.getTasteStatistic(), taste);
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

    private void updateDrinkSituationStatistic(final SituationStatistic situationStatistic,
                                               final SituationDto situationDto) {
        situationStatistic.updateSituationStatistic(situationDto);
    }

    private void updateDrinkTasteStatistic(final TasteStatistic tasteStatistic,
                                           final TasteDto tasteDto) {
        tasteStatistic.updateTasteRating(tasteDto);
    }

    private void downgradeDrinkSituationStatistic(final SituationStatistic situationStatistic,
                                                  final Situation situation) {
        situationStatistic.downgradeSituationStatistic(situation);
    }

    private void downgradeDrinkTasteStatistic(final TasteStatistic tasteStatistic,
                                              final Taste taste) {
        tasteStatistic.downgradeTasteRating(taste);
    }
}
