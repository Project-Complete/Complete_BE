package org.complete.challang.account.user.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.complete.challang.drink.domain.entity.Drink;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "drink_bookmark_id"))
@Entity
public class DrinkBookmark {

    @ManyToOne(fetch = FetchType.LAZY)
    private Drink drink;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
