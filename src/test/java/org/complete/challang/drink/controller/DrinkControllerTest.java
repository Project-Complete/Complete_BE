package org.complete.challang.drink.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.complete.challang.common.exception.ApiException;
import org.complete.challang.common.exception.ErrorCode;
import org.complete.challang.common.exception.GlobalExceptionHandler;
import org.complete.challang.drink.common.adaptor.LocalDateTimeTypeAdaptor;
import org.complete.challang.drink.domain.entity.Drink;
import org.complete.challang.drink.repository.DrinkFindResponse;
import org.complete.challang.drink.service.DrinkService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class DrinkControllerTest {

    @InjectMocks
    private DrinkController drinkController;

    @Mock
    private DrinkService drinkService;

    private MockMvc mockMvc;
    private Gson gson;

    @BeforeEach
    public void init() {
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdaptor())
                .create();
        mockMvc = MockMvcBuilders.standaloneSetup(drinkController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("MockMvc가 존재")
    public void existMockMvc() throws Exception {
        assertThat(drinkController).isNotNull();
        assertThat(mockMvc).isNotNull();
    }

    @Test
    @DisplayName("주류 상세조회시 Drink가 존재하지 않음")
    public void notExistDrink() throws Exception {
        // given
        final String url = "/drink/detail/-1";
        doThrow(new ApiException(ErrorCode.DRINK_NOT_FOUND))
                .when(drinkService)
                .findDetailDrink(-1L);

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
        );

        // then
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("주류 상세조회 성공")
    public void 멤버십상세조회성공() throws Exception {
        // given
        final String url = "/drink/detail/1";
        doReturn(drinkFindResponse())
                .when(drinkService)
                .findDetailDrink(1L);

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
        );

        // then
        resultActions.andExpect(status().isOk());

        DrinkFindResponse result = gson.fromJson(resultActions.andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8), DrinkFindResponse.class);

        assertThat(result.getDrinkId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("트롤브루 레몬 라들러");
    }

    private DrinkFindResponse drinkFindResponse() {
        Drink drink = Drink.builder()
                .id(1L)
                .name("트롤브루 레몬 라들러")
                .summary("유럽식 상큼한 레몬향이 풍부하게 느껴지는 라들러")
                .description("만하임 지방")
                .abv(5.0)
                .imageUrl("http://localhost")
                .reviewCount(0L)
                .reviewSumRating(0L)
                .build();
        return drink.toDto();
    }
}
