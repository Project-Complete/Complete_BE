package org.complete.challang.review.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.complete.challang.account.user.domain.entity.User;
import org.complete.challang.common.domain.entity.BaseEntity;
import org.complete.challang.drink.domain.entity.Drink;

import java.util.ArrayList;
import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "review_id"))
@Entity
public class Review extends BaseEntity {

    @Column(nullable = false)
    private String imageUrl;

    @Min(0)
    @Max(5)
    @Column(nullable = false)
    private float rating;

    @Size(min = 20, max = 255)
    @Column(nullable = false)
    private String content;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    private Situation situation;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    private Taste taste;

    @ManyToOne(fetch = FetchType.LAZY)
    private Drink drink;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder.Default
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<ReviewFlavor> reviewFlavors = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<ReviewFood> reviewFoods = new ArrayList<>();

    public void deleteReview() {
        super.delete();
    }
}
