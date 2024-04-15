package org.complete.challang.app.drink.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.complete.challang.app.account.user.domain.entity.DrinkLike;
import org.complete.challang.app.account.user.domain.entity.User;
import org.complete.challang.app.common.domain.entity.BaseEntity;
import org.complete.challang.app.common.exception.ApiException;
import org.complete.challang.app.common.exception.ErrorCode;
import org.complete.challang.app.review.domain.entity.Review;

import java.util.ArrayList;
import java.util.List;

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

    @Builder.Default
    @Embedded
    private FoodStatistic foodStatistic = new FoodStatistic();

    @Builder.Default
    @Embedded
    private SituationStatistic situationStatistic = new SituationStatistic();

    @Builder.Default
    @Embedded
    private TasteStatistic tasteStatistic = new TasteStatistic();

    @ManyToOne(fetch = FetchType.LAZY)
    private DrinkDetailType drinkDetailType;

    @ManyToOne(fetch = FetchType.LAZY)
    private DrinkManufacturer drinkManufacturer;

    @Builder.Default
    @OneToMany(mappedBy = "drink", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DrinkLike> drinkLikes = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "drink", cascade = CascadeType.ALL, orphanRemoval = true)
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

    public void likeDrink(final User user) {
        final DrinkLike drinkLike = DrinkLike.builder()
                .user(user)
                .drink(this)
                .build();

        if (drinkLikes.contains(drinkLike)) {
            throw new ApiException(ErrorCode.DRINK_LIKE_CONFLICT);
        }

        drinkLikes.add(drinkLike);
    }

    public void unLikeDrink(final User user) {
        final DrinkLike drinkLike = DrinkLike.builder()
                .user(user)
                .drink(this)
                .build();

        if (!drinkLikes.contains(drinkLike)) {
            throw new ApiException(ErrorCode.DRINK_LIKE_NOT_FOUND);
        }

        drinkLikes.remove(drinkLike);
    }

    public void updateDrinkManufacturer(final DrinkManufacturer drinkManufacturer) {
        this.drinkManufacturer = drinkManufacturer;
    }

    public void updateDrinkDetailType(final DrinkDetailType drinkDetailType) {
        this.drinkDetailType = drinkDetailType;
    }

    public void addDrinkPackages(final Package packages) {
        final DrinkPackage drinkPackage = DrinkPackage.builder()
                .drink(this)
                .packages(packages)
                .build();

        if (drinkPackages.contains(drinkPackage)) {
            throw new ApiException(ErrorCode.DRINK_PACKAGE_CONFLICT);
        }

        drinkPackages.add(drinkPackage);
    }
}
