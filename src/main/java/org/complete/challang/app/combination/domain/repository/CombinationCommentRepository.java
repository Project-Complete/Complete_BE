package org.complete.challang.app.combination.domain.repository;

import org.complete.challang.app.combination.domain.entity.CombinationComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CombinationCommentRepository extends JpaRepository<CombinationComment, Long>, CombinationCommentCustomRepository {
}
