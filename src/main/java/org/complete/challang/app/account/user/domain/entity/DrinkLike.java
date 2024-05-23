package org.complete.challang.app.account.user.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.complete.challang.app.common.domain.entity.BaseEntity;
import org.complete.challang.app.drink.domain.entity.Drink;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "drink_like_id"))
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(
                name = "drink_user_unique",
                columnNames = {"drink_drink_id", "user_user_id"}
        )
})
@EqualsAndHashCode(callSuper = false)
public class DrinkLike extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Drink drink;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
