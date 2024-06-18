package org.complete.challang.app.combination.domain.repository;

import org.complete.challang.app.combination.domain.entity.CombinationComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CombinationCommentCustomRepository {

    Page<CombinationComment> findCommentByCombinationBoardId(final Long combinationId,
                                                             final Pageable pageable);

    Page<CombinationComment> findReplyCommentByCombinationCommentId(final Long combinationCommentId,
                                                                    final Pageable pageable);
}
