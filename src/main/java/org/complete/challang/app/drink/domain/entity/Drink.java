package org.complete.challang.app.drink.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.complete.challang.app.account.user.domain.entity.DrinkBookmark;
import org.complete.challang.app.account.user.domain.entity.DrinkLike;
import org.complete.challang.app.account.user.domain.entity.User;
import org.complete.challang.app.common.domain.entity.BaseEntity;
import org.complete.challang.app.common.exception.ApiException;
import org.complete.challang.app.common.exception.ErrorCode;
import org.complete.challang.app.drink.controller.dto.item.*;
import org.complete.challang.app.drink.controller.dto.response.*;
import org.complete.challang.app.review.domain.entity.Review;

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

    @Column(length = 400)
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
    @OneToMany(mappedBy = "drink", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DrinkLike> drinkLikes = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "drink", cascade = CascadeType.ALL)
    private List<DrinkPackage> drinkPackages = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "drink", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    public void updateReviewCount() {
        reviewCount++;
    }

    public void downgradeReviewCount() {
        reviewCount--;
    }

    public void updateReviewSumRating(double rating) {
        reviewSumRating += rating;
    }

    public void downgradeReviewSumRating(double rating) {
        reviewSumRating -= rating;
    }

    public DrinkFindResponse toDto() {
        return DrinkFindResponse.builder()
                .drinkId(getId())
                .name(name)
                .imageUrl(imageUrl)
                .summary(summary)
                .manufacturer(ManufacturerDto.builder()
                        .drinkManufacturerId(drinkManufacturer.getId())
                        .manufacturerName(drinkManufacturer.getManufacturerName())
                        .location(drinkManufacturer.getLocation().getLocation())
                        .build())
                .reviewRating(reviewSumRating / reviewCount)
                .tags(drinkTags.stream()
                        .map(drinkTag ->
                                TagDto.builder()
                                        .tagId(drinkTag.getTag().getId())
                                        .tag(drinkTag.getTag().getTag())
                                        .build())
                        .collect(Collectors.toList()))
                .reviewCount(reviewCount)
                .tasteStatistic(TasteAverageStatisticDto.builder()
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
                                PackageDto.builder()
                                        .type(drinkPackage.getPackages().getType())
                                        .volume(drinkPackage.getPackages().getVolume())
                                        .build())
                        .collect(Collectors.toList()))
                .abv(abv)
                .type(DrinkTypeDto.builder()
                        .type(drinkDetailType.getDrinkType().getDrinkType())
                        .detailType(drinkDetailType.getDetailType())
                        .build())
                .build();
    }

    public void likeDrink(User user) {
        DrinkLike drinkLike = DrinkLike.builder()
                .user(user)
                .drink(this)
                .build();

        if (drinkLikes.contains(drinkLike)) {
            throw new ApiException(ErrorCode.DRINK_LIKE_CONFLICT);
        }

        drinkLikes.add(drinkLike);
    }

    public void unLikeDrink(User user) {
        DrinkLike drinkLike = DrinkLike.builder()
                .user(user)
                .drink(this)
                .build();

        if (!drinkLikes.contains(drinkLike)) {
            throw new ApiException(ErrorCode.DRINK_LIKE_NOT_FOUND);
        }

        drinkLikes.remove(drinkLike);
    }
}
