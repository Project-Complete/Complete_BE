package org.complete.challang.review.domain.entity;

import jakarta.persistence.*;
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

    private String imageUrl;

    private float rating;

    private String content;

    @OneToOne(orphanRemoval = true)
    private Situation situation;

    @OneToOne(orphanRemoval = true)
    private Taste taste;

    @ManyToOne(fetch = FetchType.LAZY)
    private Drink drink;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder.Default
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<ReviewFlavor> reviewFlavors = new ArrayList<>();
}
