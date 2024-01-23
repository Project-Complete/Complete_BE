package org.complete.domain.member.oauth.service;

import lombok.RequiredArgsConstructor;
import org.complete.domain.member.entity.Member;
import org.complete.domain.member.oauth.domain.PrincipalDetails;
import org.complete.domain.member.oauth.domain.ProviderType;
import org.complete.domain.member.oauth.provider.OAuth2UserInfo;
import org.complete.domain.member.oauth.provider.OAuth2UserInfoFactory;
import org.complete.domain.member.repository.MemberRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PrincipalOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        return this.process(userRequest, oAuth2User);
    }

    private OAuth2User process(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        ProviderType providerType = ProviderType.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId().toUpperCase());
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, oAuth2User.getAttributes());

        Member member = memberRepository.findBySocialId(oAuth2UserInfo.getId());
        if (member != null) {
            if (member.getProviderType() != providerType) {
                // MissMatch Exception
                throw new OAuth2AuthenticationException("제공자 불일치");
            }
        }

        Member createdMember = createMember(oAuth2UserInfo, providerType);
        return PrincipalDetails.from(createdMember, oAuth2User.getAttributes());
    }

    private Member createMember(OAuth2UserInfo oAuth2UserInfo, ProviderType providerType) {
        Member member = Member.builder()
                .socialId(oAuth2UserInfo.getId())
                .username(oAuth2UserInfo.getName())
                .email(oAuth2UserInfo.getEmail())
                .imageUrl(oAuth2UserInfo.getImageUrl())
                .providerType(providerType)
                .build();

        return memberRepository.saveAndFlush(member);
    }
}
