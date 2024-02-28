package org.complete.challang.app.drink.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.complete.challang.app.common.domain.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "location_id"))
@Entity
public class Location extends BaseEntity {

    private String location;

    @Builder.Default
    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    private List<DrinkManufacturer> drinkManufacturers = new ArrayList<>();
}
