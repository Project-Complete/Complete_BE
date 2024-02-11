package org.complete.challang.account.user.domain.repository;

import org.complete.challang.account.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
