package org.complete.challang.app.combination.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.complete.challang.app.account.user.domain.entity.User;
import org.complete.challang.app.combination.controller.dto.request.CombinationBoardUpdateRequest;
import org.complete.challang.app.common.domain.entity.BaseEntity;
import org.complete.challang.app.common.exception.ApiException;
import org.complete.challang.app.common.exception.ErrorCode;
import org.hibernate.annotations.DynamicUpdate;

import java.util.HashSet;
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

    @Builder.Default
    @OneToMany(mappedBy = "combinationBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Combination> combinations = new HashSet<>();

    @OneToMany(mappedBy = "combinationBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CombinationBoardLike> combinationBoardLikes;

    @OneToMany(mappedBy = "combinationBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CombinationBoardBookmark> combinationBoardBookmarks;

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

    public void deleteCombinationBoard() {
        super.delete();
    }

    public void likeCombinationBoard(final User user) {
        CombinationBoardLike combinationBoardLike = CombinationBoardLike.builder()
                .combinationBoard(this)
                .user(user)
                .build();

        if (combinationBoardLikes.contains(combinationBoardLike)) {
            throw new ApiException(ErrorCode.COMBINATION_BOARD_LIKE_CONFLICT);
        }

        combinationBoardLikes.add(combinationBoardLike);
    }

    public void unLikeCombinationBoard(final User user) {
        CombinationBoardLike combinationBoardLike = CombinationBoardLike.builder()
                .combinationBoard(this)
                .user(user)
                .build();

        if (!combinationBoardLikes.contains(combinationBoardLike)) {
            throw new ApiException(ErrorCode.COMBINATION_BOARD_LIKE_NOT_FOUND);
        }

        combinationBoardLikes.remove(combinationBoardLike);
    }

    public void createBookmark(final User user) {
        CombinationBoardBookmark combinationBoardBookmark = CombinationBoardBookmark.builder()
                .combinationBoard(this)
                .user(user)
                .build();

        if (combinationBoardBookmarks.contains(combinationBoardBookmark)) {
            throw new ApiException(ErrorCode.COMBINATION_BOARD_BOOKMARK_CONFLICT);
        }

        combinationBoardBookmarks.add(combinationBoardBookmark);
    }

    public void deleteBookmark(final User user) {
        CombinationBoardBookmark combinationBoardBookmark = CombinationBoardBookmark.builder()
                .combinationBoard(this)
                .user(user)
                .build();

        if (!combinationBoardBookmarks.contains(combinationBoardBookmark)) {
            throw new ApiException(ErrorCode.COMBINATION_BOARD_BOOKMARK_NOT_FOUND);
        }

        combinationBoardBookmarks.remove(combinationBoardBookmark);
    }
}
