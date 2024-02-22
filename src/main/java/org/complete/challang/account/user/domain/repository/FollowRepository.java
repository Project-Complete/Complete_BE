package org.complete.challang.account.user.domain.repository;

import org.complete.challang.account.user.domain.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    boolean existsByFromUserIdAndToUserId(final Long fromUserId, final Long toUserId);
}
