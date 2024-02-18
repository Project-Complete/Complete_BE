package org.complete.challang.app.review.controller;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.complete.challang.common.exception.ApiException;
import org.complete.challang.common.exception.GlobalExceptionHandler;
import org.complete.challang.review.controller.ReviewController;
import org.complete.challang.review.controller.dto.item.SituationDto;
import org.complete.challang.review.controller.dto.item.TasteDto;
import org.complete.challang.review.controller.dto.request.ReviewCreateRequest;
import org.complete.challang.review.controller.dto.response.ReviewCreateResponse;
import org.complete.challang.review.service.ReviewService;
import org.complete.challang.util.LocalDateTimeTypeAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.complete.challang.common.exception.ErrorCode.USER_NOT_FOUND;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ReviewControllerTest {

    public static final String ACCESS_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwicm9sZSI6IlVTRVIiLCJpYXQiOjE3MDgwNjA1OTgsImV4cCI6MTcwODA2MjM5OH0.R3PYWVXy88IpRS7rAArGPy6H4raUejK2oUFJmBt86gY";

    @InjectMocks
    private ReviewController target;

    @Mock
    private ReviewService reviewService;

    private MockMvc mockMvc;
    private Gson gson;
    private final Long drinkId = 1L;
    private final Long reviewId = 1L;
    private final Long userId = 1L;
    private final String imageUrl = "imageUrl";
    private final String content = "content content content content content";
    private final float rating = 3;
    private final List<Long> flavorIds = Arrays.asList(1L, 2L, 3L);
    private final List<Long> foodIds = Arrays.asList(1L, 2L, 3L);
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

    private final Authentication authentication = new TestingAuthenticationToken("test1@gmail.com", null, "USER");

    @BeforeEach
    public void init() {
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        mockMvc = MockMvcBuilders.standaloneSetup(target)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @ParameterizedTest
    @MethodSource("invalidReviewCreateParameter")
    public void 주류리뷰등록실패_잘못된파라미터(final Long drinkId,
                                 final String imageUrl,
                                 final String content,
                                 final float rating) throws Exception {
        // given
        final String url = "/reviews";

        // when
        final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .header(HttpHeaders.AUTHORIZATION, "1234")
                .content(gson.toJson(createReviewCreateRequest(drinkId,
                        imageUrl,
                        content,
                        rating,
                        situationDto,
                        tasteDto,
                        flavorIds,
                        foodIds)))
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    public void 주류리뷰등록실패_ReviewService에서에러Throw() throws Exception {
        // given
        final String url = "/reviews";

        doThrow(new ApiException(USER_NOT_FOUND)).when(reviewService)
                .createReview(any(ReviewCreateRequest.class), eq(userId));

        // when
        final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .content(gson.toJson(reviewCreateRequest))
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    public void 주류리뷰등록성공() throws Exception {
        // given
        final String url = "/reviews";
        final ReviewCreateResponse reviewCreateResponse = ReviewCreateResponse.builder()
                .reviewId(reviewId)
                .imageUrl(imageUrl)
                .content(content)
                .rating(rating)
                .reviewCreatedDate(LocalDateTime.now())
                .build();

        doReturn(reviewCreateResponse).when(reviewService)
                .createReview(any(ReviewCreateRequest.class), userId);

        // when
        final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .content(gson.toJson(reviewCreateRequest))
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isCreated());

        final ReviewCreateResponse response = gson.fromJson(resultActions.andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8), ReviewCreateResponse.class);

        assertThat(response.getReviewId()).isEqualTo(reviewId);
        assertThat(response.getImageUrl()).isEqualTo(imageUrl);
        assertThat(response.getContent()).isEqualTo(content);
        assertThat(response.getRating()).isEqualTo(rating);
    }

    private static Stream<Arguments> invalidReviewCreateParameter() {
        return Stream.of(
                Arguments.of(null, "imageUrl", "content content content content content", 4),
                Arguments.of(1L, null, "content content content content content", 4),
                Arguments.of(1L, "imageUrl", "content", 4),
                Arguments.of(1L, "imageUrl", "content content content content content", 6)
        );
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
