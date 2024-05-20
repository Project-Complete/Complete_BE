package org.complete.challang.app.combination.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.complete.challang.app.account.user.domain.entity.User;
import org.complete.challang.app.common.domain.entity.BaseEntity;

import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "combination_board_id"))
@Entity
public class CombinationBoard extends BaseEntity {

    private String title;

    private String content;

    private String imageUrl;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "combinationBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Combination> combinations;

    public void updateCombinations(final List<Combination> combinations) {
        this.combinations = combinations;
    }
}
