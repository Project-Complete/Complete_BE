package org.complete.challang.drink.service;

import org.complete.challang.common.exception.ApiException;
import org.complete.challang.common.exception.ErrorCode;
import org.complete.challang.drink.domain.entity.Package;
import org.complete.challang.drink.domain.entity.*;
import org.complete.challang.drink.dto.response.DrinkFindResponse;
import org.complete.challang.drink.dto.response.FlavorStatisticFindResponse;
import org.complete.challang.drink.dto.response.FoodStatisticFindResponse;
import org.complete.challang.drink.repository.DrinkRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
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
        doReturn(foodStatisticFindResponses()).when(drinkRepository).findFoodStatisticById(anyLong());
        doReturn(flavorStatisticFindResponses()).when(drinkRepository).findFlavorStatisticById(anyLong());

        //when
        final DrinkFindResponse drinkFindResponse = drinkService.findDetailDrink(anyLong());

        //then
        assertThat(drinkFindResponse.getDrinkId()).isEqualTo(1L);
        assertThat(drinkFindResponse.getName()).isEqualTo("트롤브루 레몬 라들러");
        assertThat(drinkFindResponse.getTags().get(0).getTag()).isEqualTo("태그");
        assertThat(drinkFindResponse.getPackages().get(0).getType()).isEqualTo("패키지");
        assertThat(drinkFindResponse.getManufacturer().getManufacturerName()).isEqualTo("제조사");
        assertThat(drinkFindResponse.getManufacturer().getLocation()).isEqualTo("지역");
        assertThat(drinkFindResponse.getReviewRating()).isEqualTo(4.25);
        assertThat(drinkFindResponse.getTasteStatistic().getSweetRating()).isEqualTo(0L);
        assertThat(drinkFindResponse.getFoodStatistics().get(0).getCategory()).isEqualTo("육류");
        assertThat(drinkFindResponse.getFlavorStatistics().get(0).getFlavor()).isEqualTo("만다린");
        assertThat(drinkFindResponse.getType().getType()).isEqualTo("주종");
        assertThat(drinkFindResponse.getType().getDetailType()).isEqualTo("세부주종");
    }


    private List<FlavorStatisticFindResponse> flavorStatisticFindResponses() {
        return List.of(FlavorStatisticFindResponse.builder()
                        .flavorId(1L)
                        .flavor("만다린")
                        .count(2L)
                        .build(),
                FlavorStatisticFindResponse.builder()
                        .flavorId(2L)
                        .flavor("베리")
                        .count(1L)
                        .build()
        );
    }

    private List<FoodStatisticFindResponse> foodStatisticFindResponses() {
        return List.of(FoodStatisticFindResponse.builder()
                        .foodId(1L)
                        .category("육류")
                        .imageUrl("http://localhost")
                        .count(2L)
                        .build(),
                FoodStatisticFindResponse.builder()
                        .foodId(2L)
                        .category("가공육")
                        .imageUrl("http://localhost")
                        .count(1L)
                        .build()
        );
    }

    private Drink drink() {
        Drink drink = Drink.builder()
                .id(1L)
                .name("트롤브루 레몬 라들러")
                .imageUrl("http://localhost")
                .summary("유럽식 상큼한 레몬향이 풍부하게 느껴지는 라들러")
                .drinkManufacturer(drinkManufacturer())
                .drinkDetailType(drinkDetailType())
                .reviewSumRating(8.5)
                .reviewCount(2L)
                .tasteStatistic(TasteStatistic.builder().build())
                .situationStatistic(SituationStatistic.builder().build())
                .title("유럽식 레몬 라들러 스타일 기반 과일맥주")
                .description("만하임 지방")
                .abv(5.0)
                .build();

        drink.getDrinkTags().add(drinkTag(drink));
        drink.getDrinkPackages().add(drinkPackage(drink));

        return drink;
    }

    private DrinkManufacturer drinkManufacturer() {
        return DrinkManufacturer.builder()
                .id(1L)
                .manufacturerName("제조사")
                .location(Location.builder()
                        .id(1L)
                        .location("지역")
                        .build())
                .build();
    }

    private DrinkTag drinkTag(Drink drink) {
        return DrinkTag.builder()
                .id(1L)
                .drink(drink)
                .tag(Tag.builder()
                        .id(1L)
                        .tag("태그")
                        .build())
                .build();
    }

    private DrinkPackage drinkPackage(Drink drink) {
        return DrinkPackage.builder()
                .id(1L)
                .drink(drink)
                .packages(Package.builder()
                        .id(1L)
                        .type("패키지")
                        .volume("용량")
                        .build())
                .build();
    }

    private DrinkDetailType drinkDetailType() {
        return DrinkDetailType.builder()
                .id(1L)
                .detailType("세부주종")
                .drinkType(DrinkType.builder()
                        .id(1L)
                        .drinkType("주종")
                        .build())
                .build();
    }
}
