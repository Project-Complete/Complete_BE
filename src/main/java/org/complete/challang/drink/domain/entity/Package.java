package org.complete.challang.drink.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.complete.challang.common.domain.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "package_id"))
@Entity
public class Package extends BaseEntity {

    private String type;

    private int volume;

    @Builder.Default
    @OneToMany(mappedBy = "package", cascade = CascadeType.ALL)
    private List<DrinkPackage> drinkPackages = new ArrayList<>();
}
