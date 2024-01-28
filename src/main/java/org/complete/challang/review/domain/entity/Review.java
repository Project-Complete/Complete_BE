package org.complete.challang.review.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.complete.challang.account.user.domain.entity.User;
import org.complete.challang.common.domain.entity.BaseEntity;
import org.complete.challang.drink.domain.entity.Drink;

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

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Drink drink;

    @OneToOne(orphanRemoval = true)
    private Taste taste;

    @OneToOne(orphanRemoval = true)
    private Situation situation;
}
