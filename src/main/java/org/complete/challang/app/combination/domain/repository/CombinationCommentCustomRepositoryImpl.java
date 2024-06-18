package org.complete.challang.app.combination.domain.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.complete.challang.app.combination.domain.entity.CombinationComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.complete.challang.app.combination.domain.entity.QCombinationComment.combinationComment;

@Repository
@RequiredArgsConstructor
public class CombinationCommentCustomRepositoryImpl implements CombinationCommentCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<CombinationComment> findCommentByCombinationBoardId(final Long combinationId,
                                                                    final Pageable pageable) {
        List<CombinationComment> combinationComments = jpaQueryFactory.selectFrom(combinationComment)
                .where(combinationComment.combinationBoard.id.eq(combinationId))
                .where(combinationComment.parent.isNull())
                .where(combinationComment.isActive.isTrue())
                .orderBy(combinationComment.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = jpaQueryFactory.select(combinationComment.count())
                .from(combinationComment)
                .where(combinationComment.combinationBoard.id.eq(combinationId))
                .where(combinationComment.parent.isNull())
                .where(combinationComment.isActive.isTrue());

        return PageableExecutionUtils.getPage(combinationComments, pageable, count::fetchOne);
    }

    @Override
    public Page<CombinationComment> findReplyCommentByCombinationCommentId(final Long combinationCommentId,
                                                                           final Pageable pageable) {
        List<CombinationComment> replyComments = jpaQueryFactory.selectFrom(combinationComment)
                .where(combinationComment.parent.id.eq(combinationCommentId))
                .where(combinationComment.isActive.isTrue())
                .orderBy(combinationComment.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = jpaQueryFactory.select(combinationComment.count())
                .from(combinationComment)
                .where(combinationComment.parent.id.eq(combinationCommentId))
                .where(combinationComment.isActive.isTrue());

        return PageableExecutionUtils.getPage(replyComments, pageable, count::fetchOne);
    }
}
