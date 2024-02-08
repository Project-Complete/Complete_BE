package org.complete.challang.drink.service;

import org.complete.challang.common.exception.ApiException;
import org.complete.challang.common.exception.ErrorCode;
import org.complete.challang.drink.domain.entity.Drink;
import org.complete.challang.drink.dto.response.DrinkFindResponse;
import org.complete.challang.drink.repository.DrinkRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class DrinkServiceTest {

    @InjectMocks
    private DrinkService drinkService;

    @Mock
    private DrinkRepository drinkRepository;

    @Test
    public void 주류상세조회실패_존재하지않는주류() {
        //given
        doReturn(Optional.empty()).when(drinkRepository).findById(anyLong());

        //when
        final ApiException result = assertThrows(ApiException.class, () -> drinkService.findDetailDrink(anyLong()));

        //then
        assertThat(result.getErrorCode()).isEqualTo(ErrorCode.DRINK_NOT_FOUND);
    }

    @Test
    public void 주류상세조회성공() {
        //given
        doReturn(Optional.of(drink())).when(drinkRepository).findById(anyLong());

        //when
        final DrinkFindResponse drinkFindResponse = drinkService.findDetailDrink(anyLong());

        //then
        assertThat(drinkFindResponse.getDrinkId()).isEqualTo(1L);
        assertThat(drinkFindResponse.getName()).isEqualTo("트롤브루 레몬 라들러");
    }

    private Drink drink() {
        return Drink.builder()
                .id(1L)
                .name("트롤브루 레몬 라들러")
                .summary("유럽식 상큼한 레몬향이 풍부하게 느껴지는 라들러")
                .description("만하임 지방")
                .abv(5.0)
                .imageUrl("http://localhost")
                .reviewCount(0L)
                .reviewSumRating(0L)
                .build();
    }
}
