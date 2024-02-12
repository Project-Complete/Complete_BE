package org.complete.challang.drink.controller;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.complete.challang.common.exception.ApiException;
import org.complete.challang.common.exception.ErrorCode;
import org.complete.challang.common.exception.GlobalExceptionHandler;
import org.complete.challang.drink.common.adaptor.LocalDateTimeTypeAdaptor;
import org.complete.challang.drink.dto.response.DrinkFindResponse;
import org.complete.challang.drink.service.DrinkService;
import org.junit.jupiter.api.BeforeEach;
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
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        mockMvc = MockMvcBuilders.standaloneSetup(drinkController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void mockMvc존재() throws Exception {
        assertThat(drinkController).isNotNull();
        assertThat(mockMvc).isNotNull();
    }

    @Test
    public void 주류상세조회실패_존재하지않는주류() throws Exception {
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
    public void 주류상세조회성공() throws Exception {
        // given
        final String url = "/drink/detail/1";
        doReturn(DrinkFindResponse.builder()
                .drinkId(1L)
                .build())
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
    }
}
