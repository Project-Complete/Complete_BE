package org.complete.challang.review.domain.entity;

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
@AttributeOverride(name = "id", column = @Column(name = "flavor_id"))
@Entity
public class Flavor extends BaseEntity {

    private String flavor;

    @Builder.Default
    @OneToMany(mappedBy = "flavor", cascade = CascadeType.ALL)
    private List<ReviewFlavor> reviewFlavors = new ArrayList<>();
}
