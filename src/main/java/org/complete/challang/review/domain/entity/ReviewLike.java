package org.complete.challang.review.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.complete.challang.account.user.domain.entity.User;
import org.complete.challang.common.domain.entity.BaseEntity;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "review_like_id"))
@Entity
public class ReviewLike extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Review review;
}