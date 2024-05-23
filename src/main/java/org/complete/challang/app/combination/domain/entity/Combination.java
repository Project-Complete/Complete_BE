package org.complete.challang.app.combination.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.complete.challang.app.common.domain.entity.BaseEntity;
import org.complete.challang.app.drink.domain.entity.Drink;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "combination_id"))
@Entity
public class Combination extends BaseEntity {

    private String name;

    private String volume;

    private int xCoordinate;

    private int yCoordinate;

    @ManyToOne(fetch = FetchType.LAZY)
    private CombinationBoard combinationBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    private Drink drink;
}
