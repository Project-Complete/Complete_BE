package org.complete.challang.app.combination.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.complete.challang.app.account.user.domain.entity.User;
import org.complete.challang.app.common.domain.entity.BaseEntity;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "combination_board_bookmark_id"))
@Entity
@EqualsAndHashCode(callSuper = false)
public class CombinationBoardBookmark extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private CombinationBoard combinationBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
