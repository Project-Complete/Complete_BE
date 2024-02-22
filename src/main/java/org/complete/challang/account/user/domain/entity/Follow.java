package org.complete.challang.account.user.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.complete.challang.common.domain.entity.BaseEntity;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "follow_id"))
@Entity
public class Follow extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private User fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    private User toUser;
}
