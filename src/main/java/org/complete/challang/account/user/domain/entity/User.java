package org.complete.challang.account.user.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.complete.challang.common.domain.entity.BaseEntity;

import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
@Entity
public class User extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    private String nickname;

    private String email;

    private String profileImageUrl;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<DrinkBookmark> drinkBookmark;
}
