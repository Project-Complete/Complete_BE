package org.complete.challang.drink.repository;

import lombok.*;
import org.complete.challang.drink.domain.entity.*;
import org.complete.challang.drink.domain.entity.Package;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
@Builder
public class DrinkFindResponse {

    private Long drinkId;

    private String name;

    private String summary;

    private String description;

    private double abv;

    private String imageUrl;

    private long reviewCount;

    private double reviewSumRating;

    private FoodStatistic foodStatistic;

    private SituationStatistic situationStatistic;

    private TasteStatistic tasteStatistic;

    private List<DrinkManufacturer> manufacturers;

    private List<Tag> tags;

    private List<Food> foods;

    private List<Package> packages;
}
