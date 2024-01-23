package org.complete.domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.complete.domain.member.oauth.domain.ProviderType;
import org.complete.domain.member.oauth.domain.RoleType;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "social_id")
    private String socialId;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "image_url")
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider_type")
    private ProviderType providerType;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_type")
    private RoleType roleType;

    @Builder
    public Member(Long memberId, String socialId, String username, String email, String imageUrl, ProviderType providerType) {
        this.memberId = memberId;
        this.socialId = socialId;
        this.username = username;
        this.email = email;
        this.imageUrl = imageUrl;
        this.providerType = providerType;
        this.roleType = RoleType.USER;
    }
}
