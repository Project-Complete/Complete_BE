package org.complete.challang.app.combination.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.complete.challang.app.common.domain.entity.BaseEntity;
import org.complete.challang.app.drink.domain.entity.Drink;

import java.util.Objects;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "combination_id"))
@Entity
public class Combination extends BaseEntity {

    private String name;

    private String volume;

    private float xCoordinate;

    private float yCoordinate;

    @ManyToOne(fetch = FetchType.LAZY)
    private CombinationBoard combinationBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    private Drink drink;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Combination that = (Combination) o;
        return xCoordinate == that.xCoordinate
                && yCoordinate == that.yCoordinate
                && Objects.equals(name, that.name)
                && Objects.equals(volume, that.volume)
                && Objects.equals(combinationBoard, that.combinationBoard)
                && Objects.equals(drink, that.drink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, volume, xCoordinate, yCoordinate, combinationBoard, drink);
    }
}
