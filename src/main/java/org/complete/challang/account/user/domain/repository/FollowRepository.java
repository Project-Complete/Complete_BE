package org.complete.challang.account.user.domain.repository;

import org.complete.challang.account.user.domain.entity.Follow;
import org.complete.challang.account.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    boolean existsByFromUserAndToUser(final User fromUser, final User toUser);

    void deleteByFromUserAndToUser(final User fromUser, final User toUser);
}
