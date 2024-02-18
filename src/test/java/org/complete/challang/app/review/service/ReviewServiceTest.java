package org.complete.challang.app.review.service;

import org.complete.challang.account.user.domain.entity.User;
import org.complete.challang.account.user.domain.repository.UserRepository;
import org.complete.challang.common.exception.ApiException;
import org.complete.challang.drink.domain.entity.Drink;
import org.complete.challang.drink.domain.entity.Food;
import org.complete.challang.drink.domain.entity.SituationStatistic;
import org.complete.challang.drink.domain.entity.TasteStatistic;
import org.complete.challang.drink.domain.repository.DrinkRepository;
import org.complete.challang.drink.domain.repository.FoodRepository;
import org.complete.challang.review.controller.dto.item.SituationDto;
import org.complete.challang.review.controller.dto.item.TasteDto;
import org.complete.challang.review.controller.dto.request.ReviewCreateRequest;
import org.complete.challang.review.controller.dto.response.ReviewCreateResponse;
import org.complete.challang.review.domain.entity.Flavor;
import org.complete.challang.review.domain.entity.Review;
import org.complete.challang.review.domain.repository.FlavorRepository;
import org.complete.challang.review.domain.repository.ReviewRepository;
import org.complete.challang.review.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.complete.challang.common.exception.ErrorCode.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @InjectMocks
    private ReviewService target;

    @Mock
    private DrinkRepository drinkRepository;

    @Mock
    private FlavorRepository flavorRepository;

    @Mock
    private FoodRepository foodRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserRepository userRepository;

    private final Long drinkId = 1L;
    private final Long reviewId = 1L;
    private final Long userId = 1L;
    private final String imageUrl = "imageUrl";
    private final String content = "content content content content content";
    private final float rating = 3;
    private List<Long> flavorIds = Arrays.asList(1L, 2L, 3L);
    private List<Long> foodIds = Arrays.asList(1L, 2L, 3L);
    private final SituationDto situationDto = createSituationDto(true, false, true, false, true);
    private final TasteDto tasteDto = createTasteDto(1, 2, 3, 4, 5);
    private final ReviewCreateRequest reviewCreateRequest = createReviewCreateRequest(drinkId,
            imageUrl,
            content,
            rating,
            situationDto,
            tasteDto,
            flavorIds,
            foodIds);

    @Test
    public void 주류리뷰등록실패_존재하는사용자가없음() {
        // given
        doReturn(Optional.empty()).when(userRepository)
                .findById(userId);

        // when
        final ApiException result = assertThrows(ApiException.class, () -> target.createReview(reviewCreateRequest, userId));

        // then
        assertThat(result.getErrorCode()).isEqualTo(USER_NOT_FOUND);

    }

    @Test
    public void 주류리뷰등록실패_존재하는주류가없음() {
        // given
        doReturn(Optional.of(user())).when(userRepository)
                .findById(userId);
        doReturn(Optional.empty()).when(drinkRepository)
                .findById(drinkId);

        // when
        final ApiException result = assertThrows(ApiException.class, () -> target.createReview(reviewCreateRequest, userId));

        // then
        assertThat(result.getErrorCode()).isEqualTo(DRINK_NOT_FOUND);
    }

    @Test
    public void 주류리뷰등록실패_선택한향이없음() {
        // given
        final SituationStatistic situationStatistic = situationStatistic(0, 0, 0, 0, 0);
        final TasteStatistic tasteStatistic = tasteStatistic(0, 0, 0, 0, 0);

        doReturn(Optional.of(user())).when(userRepository)
                .findById(userId);
        doReturn(Optional.of(drink(drinkId, 0, 0, situationStatistic, tasteStatistic))).when(drinkRepository)
                .findById(drinkId);
        doReturn(Optional.empty()).when(flavorRepository)
                .findById(anyLong());

        // when
        final ApiException result = assertThrows(ApiException.class, () -> target.createReview(reviewCreateRequest, userId));

        // then
        assertThat(result.getErrorCode()).isEqualTo(FLAVOR_NOT_FOUND);
    }

    @Test
    public void 주류리뷰등록실패_선택한음식이없음() {
        // given
        final SituationStatistic situationStatistic = situationStatistic(0, 0, 0, 0, 0);
        final TasteStatistic tasteStatistic = tasteStatistic(0, 0, 0, 0, 0);

        doReturn(Optional.of(user())).when(userRepository)
                .findById(userId);
        doReturn(Optional.of(drink(drinkId, 0, 0, situationStatistic, tasteStatistic))).when(drinkRepository)
                .findById(drinkId);
        doAnswer(invocation -> {
            Long flavorId = invocation.getArgument(0);
            if (flavorIds.contains(flavorId)) {
                return Optional.of(Flavor.builder()
                        .id(flavorId)
                        .build());
            }
            return Optional.empty();
        }).when(flavorRepository).findById(anyLong());
        doReturn(Optional.empty()).when(foodRepository)
                .findById(anyLong());

        // when
        final ApiException result = assertThrows(ApiException.class, () -> target.createReview(reviewCreateRequest, userId));

        // then
        assertThat(result.getErrorCode()).isEqualTo(FOOD_NOT_FOUND);
    }

    @Test
    public void 주류리뷰등록성공() {
        // given
        final SituationStatistic situationStatistic = situationStatistic(0, 0, 0, 0, 0);
        final TasteStatistic tasteStatistic = tasteStatistic(0, 0, 0, 0, 0);

        doReturn(Optional.of(user())).when(userRepository)
                .findById(userId);
        doReturn(Optional.of(drink(drinkId, 0, 0, situationStatistic, tasteStatistic))).when(drinkRepository)
                .findById(drinkId);
        doAnswer(invocation -> {
            Long flavorId = invocation.getArgument(0);
            if (flavorIds.contains(flavorId)) {
                return Optional.of(Flavor.builder()
                        .id(flavorId)
                        .build());
            }
            return Optional.empty();
        }).when(flavorRepository).findById(anyLong());
        doAnswer(invocation -> {
            Long foodId = invocation.getArgument(0);
            if (foodIds.contains(foodId)) {
                return Optional.of(Food.builder()
                        .id(foodId)
                        .build());
            }
            return Optional.empty();
        }).when(foodRepository).findById(anyLong());
        doReturn(review()).when(reviewRepository)
                .save(any(Review.class));

        // when
        final ReviewCreateResponse result = target.createReview(reviewCreateRequest, userId);

        // then
        assertThat(result.getReviewId()).isEqualTo(reviewId);
        assertThat(result.getImageUrl()).isEqualTo(imageUrl);
        assertThat(result.getContent()).isEqualTo(content);
        assertThat(result.getRating()).isEqualTo(rating);
    }

    private User user() {
        return User.builder()
                .id(userId)
                .build();
    }

    private Review review() {
        return Review.builder()
                .id(reviewId)
                .imageUrl(imageUrl)
                .content(content)
                .rating(rating)
                .build();
    }

    private Drink drink(final long drinkId,
                        final long reviewCount,
                        final double reviewSumRating,
                        final SituationStatistic situationStatistic,
                        final TasteStatistic tasteStatistic) {
        return Drink.builder()
                .id(drinkId)
                .reviewCount(reviewCount)
                .reviewSumRating(reviewSumRating)
                .situationStatistic(situationStatistic)
                .tasteStatistic(tasteStatistic)
                .build();
    }

    private SituationStatistic situationStatistic(final long adultSum,
                                                  final long partnerSum,
                                                  final long friendSum,
                                                  final long businessSum,
                                                  final long aloneSum) {
        return SituationStatistic.builder()
                .adultSum(adultSum)
                .partnerSum(partnerSum)
                .friendSum(friendSum)
                .businessSum(businessSum)
                .aloneSum(aloneSum)
                .build();
    }

    private TasteStatistic tasteStatistic(final double sweetSum,
                                          final double sourSum,
                                          final double bitterSum,
                                          final double bodySum,
                                          final double refreshSum) {
        return TasteStatistic.builder()
                .sweetSumRating(sweetSum)
                .sourSumRating(sourSum)
                .bitterSumRating(bitterSum)
                .bodySumRating(bodySum)
                .refreshSumRating(refreshSum)
                .build();
    }

    private ReviewCreateRequest createReviewCreateRequest(final Long drinkId,
                                                          final String imageUrl,
                                                          final String content,
                                                          final float rating,
                                                          final SituationDto situationDto,
                                                          final TasteDto tasteDto,
                                                          final List<Long> flavors,
                                                          final List<Long> foods) {
        return ReviewCreateRequest.builder()
                .drinkId(drinkId)
                .imageUrl(imageUrl)
                .content(content)
                .rating(rating)
                .situationDto(situationDto)
                .tasteDto(tasteDto)
                .flavors(flavors)
                .foods(foods)
                .build();
    }

    private SituationDto createSituationDto(final boolean adult,
                                            final boolean alone,
                                            final boolean business,
                                            final boolean friend,
                                            final boolean partner) {
        return SituationDto.builder()
                .adult(adult)
                .alone(alone)
                .business(business)
                .friend(friend)
                .partner(partner)
                .build();
    }

    private TasteDto createTasteDto(final float sweet,
                                    final float sour,
                                    final float bitter,
                                    final float body,
                                    final float refresh) {
        return TasteDto.builder()
                .sweet(sweet)
                .sour(sour)
                .bitter(bitter)
                .body(body)
                .refresh(refresh)
                .build();
    }
}
