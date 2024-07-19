package org.complete.challang.app.combination.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.complete.challang.app.account.user.domain.entity.User;
import org.complete.challang.app.combination.controller.dto.request.CombinationCommentUpdateRequest;
import org.complete.challang.app.common.domain.entity.BaseEntity;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "combination_comment_id"))
@DynamicUpdate
@Entity
public class CombinationComment extends BaseEntity {

    private String content;
    private int depth;

    @ManyToOne(fetch = FetchType.LAZY)
    private CombinationBoard combinationBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private CombinationComment parent;

    @Builder.Default
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<CombinationComment> children = new ArrayList<>();

    public void addReplyComment(CombinationComment combinationComment) {
        this.parent = combinationComment;
        this.depth = 1;
        parent.getChildren().add(this);
    }

    public void updateComment(CombinationCommentUpdateRequest combinationCommentUpdateRequest) {
        this.content = combinationCommentUpdateRequest.getContent();
    }

    public void deleteComment() {
        this.content = "삭제된 메시지입니다.";
        this.user = null;
    }
}
