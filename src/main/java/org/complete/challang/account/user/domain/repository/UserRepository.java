package org.complete.challang.account.user.domain.repository;

import org.complete.challang.account.user.domain.entity.SocialType;
import org.complete.challang.account.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findBySocialTypeAndSocialId(SocialType socialType, String socialId);

    Optional<User> findByRefreshToken(String refreshToken);
}
