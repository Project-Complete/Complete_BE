package org.complete.challang.app.common.controller.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import org.complete.challang.app.drink.controller.dto.response.DrinkListFindResponse;
import org.complete.challang.app.drink.controller.dto.response.DrinkPageResponse;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SearchListFindResponse {

    private DrinkPageResponse<DrinkListFindResponse> searchDrinks;

    public static SearchListFindResponse toDto(DrinkPageResponse<DrinkListFindResponse> drinks) {
        return SearchListFindResponse.builder()
                .searchDrinks(drinks)
                .build();
    }
}
