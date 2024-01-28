package org.complete.challang.drink.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.complete.challang.common.domain.entity.BaseEntity;
import org.complete.challang.review.domain.entity.ReviewFood;

import java.util.ArrayList;
import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "food_id"))
@Entity
public class Food extends BaseEntity {

    private String category;

    @Builder.Default
    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL)
    private List<ReviewFood> reviewFoods = new ArrayList<>();
}
