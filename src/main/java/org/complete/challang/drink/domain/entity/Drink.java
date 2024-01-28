package org.complete.challang.drink.domain.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.complete.challang.common.domain.entity.BaseEntity;

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
}
