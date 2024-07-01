package org.complete.challang.app.review.service;

import lombok.RequiredArgsConstructor;
import org.complete.challang.app.account.user.domain.entity.User;
import org.complete.challang.app.account.user.domain.repository.UserRepository;
import org.complete.challang.app.common.dto.PageInfoDto;
import org.complete.challang.app.common.exception.ApiException;
import org.complete.challang.app.common.exception.SuccessCode;
import org.complete.challang.app.drink.domain.entity.Drink;
import org.complete.challang.app.drink.domain.entity.SituationStatistic;
import org.complete.challang.app.drink.domain.entity.TasteStatistic;
import org.complete.challang.app.drink.domain.repository.DrinkRepository;
import org.complete.challang.app.drink.domain.repository.FoodRepository;
import org.complete.challang.app.review.controller.dto.item.ReviewDto;
import org.complete.challang.app.review.controller.dto.item.SituationDto;
import org.complete.challang.app.review.controller.dto.item.TasteDto;
import org.complete.challang.app.review.controller.dto.request.ReviewCreateRequest;
import org.complete.challang.app.review.controller.dto.response.ReviewCreateResponse;
import org.complete.challang.app.review.controller.dto.response.ReviewDetailResponse;
import org.complete.challang.app.review.controller.dto.response.ReviewListFindResponse;
import org.complete.challang.app.review.domain.entity.*;
import org.complete.challang.app.review.domain.repository.FlavorRepository;
import org.complete.challang.app.review.domain.repository.ReviewCustomRepositoryImpl;
import org.complete.challang.app.review.domain.repository.ReviewLikeRepository;
import org.complete.challang.app.review.domain.repository.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.complete.challang.app.common.exception.ErrorCode.*;
import static org.complete.challang.app.common.exception.SuccessCode.*;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final int REVIEW_LIST_SIZE = 6;

    private final DrinkRepository drinkRepository;
    private final FlavorRepository flavorRepository;
    private final FoodRepository foodRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewCustomRepositoryImpl reviewCustomRepository;
    private final ReviewLikeRepository reviewLikeRepository;
    private final UserRepository userRepository;

    @Transactional
    public ReviewCreateResponse createReview(final ReviewCreateRequest reviewCreateRequest,
                                             final Long userId) {
        final User user = findUserById(userId);
        final Drink drink = drinkRepository.findByIdAndIsActiveTrue(reviewCreateRequest.getDrinkId())
                .orElseThrow(() -> new ApiException(DRINK_NOT_FOUND));

        final Drink savedDrink = updateDrink(drink,
                reviewCreateRequest.getRating(),
                reviewCreateRequest.getSituationDto(),
                reviewCreateRequest.getTasteDto());

        final Review review = reviewCreateRequest.toEntity(savedDrink, user);
        createReviewFlavors(reviewCreateRequest.getFlavors(), review);
        createReviewFoods(reviewCreateRequest.getFoods(), review);

        final Review savedReview = reviewRepository.save(review);

        return ReviewCreateResponse.toDto(savedReview);
    }

    @Transactional(readOnly = true)
    public ReviewListFindResponse findReviewList(final UserDetails userDetails,
                                                 final Long drinkId,
                                                 final Long writerId,
                                                 final int page,
                                                 final String sort) {
        final PageRequest pageRequest = PageRequest.of(page, REVIEW_LIST_SIZE, ReviewSortCriteria.sortCriteriaOfValue(sort));
        final Long userId = userDetails == null ? 0L : Long.valueOf(userDetails.getUsername());

        Page<Review> reviews;
        reviews = reviewCustomRepository.findAllWithOption(pageRequest, drinkId, writerId);

        final List<ReviewDto> reviewDtos = reviews.stream()
                .map(review -> ReviewDto.toDto(review,
                        review.getReviewLikes()
                                .stream()
                                .anyMatch(reviewLike -> reviewLike.getUser()
                                        .getId()
                                        .equals(userId))))
                .collect(toList());

        return ReviewListFindResponse.toDto(reviewDtos,
                PageInfoDto.toDto(page,
                        REVIEW_LIST_SIZE,
                        reviews.getTotalElements(),
                        sort));
    }

    @Transactional(readOnly = true)
    public ReviewDetailResponse findReviewDetail(final UserDetails userDetails,
                                                 final Long reviewId) {
        final Long userId = findUserIdByUserDetails(userDetails);
        final Review review = findReviewById(reviewId);

        return ReviewDetailResponse.toDto(review,
                review.getReviewLikes()
                        .stream()
                        .anyMatch(reviewLike -> reviewLike.getUser()
                                .getId()
                                .equals(userId)),
                review.getReviewFlavors()
                        .stream()
                        .map(reviewFlavor -> reviewFlavor.getFlavor().getFlavor())
                        .collect(toList()),
                review.getReviewFoods()
                        .stream()
                        .map(reviewFood -> reviewFood.getFood().getCategory())
                        .collect(toList())
        );
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

        return REVIEW_DELETE_SUCCESS;
    }

    @Transactional
    public SuccessCode createReviewLike(final Long userId,
                                        final Long reviewId) {
        final User user = findUserById(userId);
        final Review review = findReviewById(reviewId);

        if (reviewLikeRepository.existsByUserAndReview(user, review)) {
            throw new ApiException(REVIEW_LIKE_CONFLICT);
        }

        final ReviewLike reviewLike = ReviewLike.builder()
                .user(user)
                .review(review)
                .build();
        reviewLikeRepository.save(reviewLike);

        return REVIEW_LIKE_SUCCESS;
    }

    @Transactional
    public SuccessCode deleteReviewLike(final Long userId,
                                        final Long reviewId) {
        final User user = findUserById(userId);
        final Review review = findReviewById(reviewId);

        if (!reviewLikeRepository.existsByUserAndReview(user, review)) {
            throw new ApiException(REVIEW_LIKE_NOT_FOUND);
        }
        reviewLikeRepository.deleteByUserAndReview(user, review);

        return REVIEW_LIKE_DELETE_SUCCESS;
    }

    private User findUserById(final Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(USER_NOT_FOUND));
    }

    private Long findUserIdByUserDetails(final UserDetails userDetails) {
        return userDetails == null ? 0L : Long.valueOf(userDetails.getUsername());
    }

    private Review findReviewById(final Long reviewId) {
        return reviewRepository.findByIdAndIsActiveTrue(reviewId)
                .orElseThrow(() -> new ApiException(REVIEW_NOT_FOUND));
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

    private void createReviewFlavors(final List<Long> flavors,
                                     final Review review) {
        flavors.stream()
                .forEach(flavorId -> {
                            ReviewFlavor reviewFlavor = ReviewFlavor.builder()
                                    .flavor(flavorRepository.findById(flavorId)
                                            .orElseThrow(() -> new ApiException(FLAVOR_NOT_FOUND)))
                                    .build();
                            review.addReviewFlavor(reviewFlavor);
                        }
                );
    }

    private void createReviewFoods(final List<Long> foods,
                                   final Review review) {
        foods.stream()
                .forEach(foodId -> {
                            ReviewFood reviewFood = ReviewFood.builder()
                                    .food(foodRepository.findById(foodId)
                                            .orElseThrow(() -> new ApiException(FOOD_NOT_FOUND)))
                                    .build();
                            review.addReviewFood(reviewFood);
                        }
                );
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
