package org.complete.challang.drink.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.complete.challang.account.user.domain.entity.DrinkBookmark;
import org.complete.challang.account.user.domain.entity.DrinkLike;
import org.complete.challang.common.domain.entity.BaseEntity;
import org.complete.challang.drink.dto.response.*;
import org.complete.challang.review.domain.entity.Review;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "drink_id"))
@Entity
public class Drink extends BaseEntity {

    private String name;

    private String summary;

    private String description;

    private double abv;

    private String imageUrl;

    private long reviewCount;

    private double reviewSumRating;

    private String title;

    @Embedded
    private FoodStatistic foodStatistic;

    @Embedded
    private SituationStatistic situationStatistic;

    @Embedded
    private TasteStatistic tasteStatistic;

    @ManyToOne(fetch = FetchType.LAZY)
    private DrinkDetailType drinkDetailType;

    @OneToOne(fetch = FetchType.LAZY)
    private DrinkManufacturer drinkManufacturer;

    @Builder.Default
    @OneToMany(mappedBy = "drink", cascade = CascadeType.ALL)
    private List<DrinkBookmark> drinkBookmarks = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "drink", cascade = CascadeType.ALL)
    private List<DrinkFood> drinkFoods = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "drink", cascade = CascadeType.ALL)
    private List<DrinkLike> drinkLikes = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "drink", cascade = CascadeType.ALL)
    private List<DrinkPackage> drinkPackages = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "drink", cascade = CascadeType.ALL)
    private List<DrinkTag> drinkTags = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "drink", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    public DrinkFindResponse toDto() {
        return DrinkFindResponse.builder()
                .drinkId(getId())
                .name(name)
                .imageUrl(imageUrl)
                .summary(summary)
                .manufacturer(ManufacturerFindResponse.builder()
                        .drinkManufacturerId(drinkManufacturer.getId())
                        .manufacturerName(drinkManufacturer.getManufacturerName())
                        .location(drinkManufacturer.getLocation().getLocation())
                        .build())
                .reviewRating(reviewSumRating / reviewCount)
                .tags(drinkTags.stream()
                        .map(drinkTag ->
                                TagFindResponse.builder()
                                        .tagId(drinkTag.getTag().getId())
                                        .tag(drinkTag.getTag().getTag())
                                        .build())
                        .collect(Collectors.toList()))
                .reviewCount(reviewCount)
                .tasteStatistic(TasteAverageStatistic.builder()
                        .sweetRating(tasteStatistic.getSweetSumRating() / reviewCount)
                        .sourRating(tasteStatistic.getSourSumRating() / reviewCount)
                        .bitterRating(tasteStatistic.getBitterSumRating() / reviewCount)
                        .bodyRating(tasteStatistic.getBodySumRating() / reviewCount)
                        .refreshRating(tasteStatistic.getRefreshSumRating() / reviewCount)
                        .build())
                .situationStatistic(situationStatistic)
                .title(title)
                .description(description)
                .packages(drinkPackages.stream()
                        .map(drinkPackage ->
                                PackageFindResponse.builder()
                                        .type(drinkPackage.getPackages().getType())
                                        .volume(drinkPackage.getPackages().getVolume())
                                        .build())
                        .collect(Collectors.toList()))
                .abv(abv)
                .type(DrinkTypeFindResponse.builder()
                        .type(drinkDetailType.getDrinkType().getDrinkType())
                        .detailType(drinkDetailType.getDetailType())
                        .build())
                .build();
    }
}
