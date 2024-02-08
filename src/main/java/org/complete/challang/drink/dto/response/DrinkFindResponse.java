package org.complete.challang.drink.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.complete.challang.drink.domain.entity.*;
import org.complete.challang.drink.domain.entity.Package;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
@Builder
public class DrinkFindResponse {

    private Long drinkId;

    private String name;

    private String summary;

    private DrinkManufacturer manufacturer;

    private double reviewRating;

    private List<Tag> tags;

    private long reviewCount;

    private FoodStatistic foodStatistic;

    private SituationStatistic situationStatistic;

//    private flavorStatistic flavorStatistic;

    private TasteStatistic tasteStatistic;

    private String description;

    private double abv;

    private String imageUrl;

    private List<Food> foods;

    private List<Package> packages;
}
