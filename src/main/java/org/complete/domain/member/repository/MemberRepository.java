package org.complete.domain.member.repository;

import org.complete.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findBySocialId(String socialId);
}
