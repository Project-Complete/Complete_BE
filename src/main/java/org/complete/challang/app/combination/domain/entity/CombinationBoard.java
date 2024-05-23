package org.complete.challang.app.combination.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.complete.challang.app.account.user.domain.entity.User;
import org.complete.challang.app.combination.controller.dto.request.CombinationBoardUpdateRequest;
import org.complete.challang.app.common.domain.entity.BaseEntity;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Optional;
import java.util.Set;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "combination_board_id"))
@DynamicUpdate
@Entity
public class CombinationBoard extends BaseEntity {

    private String title;

    private String content;

    private String imageUrl;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "combinationBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Combination> combinations;

    public void updateCombinations(final Set<Combination> combinations) {
        if (this.combinations.size() == combinations.size() && this.combinations.equals(combinations)) {
            return;
        }
        this.combinations.clear();
        this.combinations.addAll(combinations);
    }

    public void updateCombinationBoard(CombinationBoardUpdateRequest combinationBoardUpdateRequest) {
        Optional.ofNullable(combinationBoardUpdateRequest.getTitle())
                .ifPresent(title -> this.title = title);
        Optional.ofNullable(combinationBoardUpdateRequest.getContent())
                .ifPresent(content -> this.content = content);
        Optional.ofNullable(combinationBoardUpdateRequest.getImageUrl())
                .ifPresent(imageUrl -> this.imageUrl = imageUrl);
        Optional.ofNullable(combinationBoardUpdateRequest.getDescription())
                .ifPresent(description -> this.description = description);
    }
}
