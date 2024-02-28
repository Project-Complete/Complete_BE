package org.complete.challang.app.review.domain.entity;

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
@AttributeOverride(name = "id", column = @Column(name = "review_flavor_id"))
@Entity
public class ReviewFlavor extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Flavor flavor;

    @ManyToOne(fetch = FetchType.LAZY)
    private Review review;

    public void updateReview(Review review) {
        this.review = review;
    }
}
