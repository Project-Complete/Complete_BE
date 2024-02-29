package org.complete.challang.app.drink.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.complete.challang.app.common.domain.entity.BaseEntity;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "drink_detail_type_id"))
@Entity
public class DrinkDetailType extends BaseEntity {

    private String detailType;

    @ManyToOne(fetch = FetchType.LAZY)
    private DrinkType drinkType;
}
