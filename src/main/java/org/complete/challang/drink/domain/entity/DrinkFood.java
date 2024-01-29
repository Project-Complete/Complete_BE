package org.complete.challang.drink.domain.entity;

import jakarta.persistence.*;
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
@AttributeOverride(name = "id", column = @Column(name = "drink_food_id"))
@Entity
public class DrinkFood extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Drink drink;

    @ManyToOne(fetch = FetchType.LAZY)
    private Food food;
}
