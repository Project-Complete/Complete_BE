package org.complete.challang.app.drink.domain.entity.view;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

    @Entity(name = "drinkFoodRank")
    @Subselect(
            "select r.drink_drink_id, f.food_id, f.category, f.image_url, count(f.food_id) as fcount, row_number() over (partition by r.drink_drink_id order by count(f.food_id) desc) as rn "
                    + "from review r "
                    + "join review_food rf on r.review_id = rf.review_review_id "
                    + "join food f on rf.food_food_id = f.food_id "
                    + "group by drink_drink_id, f.food_id"
    )
    @Immutable
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public class DrinkFoodRank {

    @Id
    private Long rankId;

    @Column(name = "rn")
    private Long rn;

    @Column(name = "drink_drink_id")
    private Long drinkId;

    @Column(name = "food_id")
    private Long foodId;

    @Column(name = "category")
    private String category;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "fcount")
    private long fCount;
}
