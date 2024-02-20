package org.complete.challang.account.user.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.complete.challang.account.oauth2.CustomOAuth2User;
import org.complete.challang.common.domain.entity.BaseEntity;
import org.complete.challang.review.domain.entity.Review;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    private String socialId;

    private String nickname;

    private String email;

    private String profileImageUrl;

    @Builder.Default
    private Long followers = 0L;

    @Builder.Default
    private Long followings = 0L;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    private String refreshToken;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<DrinkBookmark> drinkBookmarks = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<DrinkLike> drinkLikes = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    public CustomOAuth2User toOAuth2User(Map<String, Object> attributes, String nameAttributeKey) {
        return CustomOAuth2User.builder()
                .email(this.email)
                .roleType(this.roleType)
                .authorities(Collections.singletonList(new SimpleGrantedAuthority(roleType.getRole())))
                .attributes(attributes)
                .nameAttributeKey(nameAttributeKey)
                .build();
    }

    public void updateEmail(final String email) {
        this.email = email;
    }

    public void updateNickname(final String nickname) {
        this.nickname = nickname;
    }

    public void updateProfileImageUrl(final String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void updateRefreshToken(final String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
