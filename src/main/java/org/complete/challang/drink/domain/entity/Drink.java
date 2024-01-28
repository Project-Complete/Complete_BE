package org.complete.challang.drink.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.complete.challang.account.user.domain.entity.DrinkBookmark;
import org.complete.challang.account.user.domain.entity.DrinkLike;
import org.complete.challang.common.domain.entity.BaseEntity;
import org.complete.challang.review.domain.entity.Review;

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

    private float abv;

    private String imageUrl;

    private long reviewCount;

    private float reviewSumRating;

    private float sweetSumRating;

    private float sourSumRating;

    private float bitterSumRating;

    private float bodySumRating;

    private float refreshSumRating;

    private Long adultSum;

    private Long partnerSum;

    private Long friendSum;

    private Long businessSum;

    private Long aloneSum;

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
    private List<DrinkTag> drinkTags = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "drink", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();
}
