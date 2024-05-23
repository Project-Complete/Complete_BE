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
@AttributeOverride(name = "id", column = @Column(name = "package_id"))
@Entity
public class Package extends BaseEntity {

    private String type;

    private String volume;

    @Builder.Default
    @OneToMany(mappedBy = "packages", cascade = CascadeType.ALL)
    private List<DrinkPackage> drinkPackages = new ArrayList<>();
}
