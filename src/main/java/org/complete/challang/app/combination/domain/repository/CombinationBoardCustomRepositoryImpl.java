package org.complete.challang.app.combination.domain.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.complete.challang.app.combination.controller.dto.response.CombinationBoardListFindResponse;
import org.complete.challang.app.combination.domain.entity.CombinationSortCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import static org.complete.challang.app.account.user.domain.entity.QUser.user;
import static org.complete.challang.app.combination.domain.entity.QCombinationBoard.combinationBoard;
import static org.complete.challang.app.combination.domain.entity.QCombinationBoardBookmark.combinationBoardBookmark;
import static org.complete.challang.app.combination.domain.entity.QCombinationBoardLike.combinationBoardLike;

@RequiredArgsConstructor
@Repository
public class CombinationBoardCustomRepositoryImpl implements CombinationBoardCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<CombinationBoardListFindResponse> findAllBySorted(final CombinationSortCriteria combinationSortCriteria,
                                                                  final Pageable pageable,
                                                                  final Long userId) {
        JPAQuery<CombinationBoardListFindResponse> query = jpaQueryFactory.select(
                        Projections.constructor(
                                CombinationBoardListFindResponse.class,
                                combinationBoard.id,
                                combinationBoard.imageUrl,
                                combinationBoard.title,
                                user.profileImageUrl,
                                user.nickname,
                                combinationBoardLike.count().eq(1L),
                                combinationBoardBookmark.count().eq(1L)
                        )
                )
                .from(combinationBoard)
                .join(combinationBoard.user, user)
                .leftJoin(combinationBoard.combinationBoardLikes, combinationBoardLike).on(combinationBoardLike.user.id.eq(userId))
                .leftJoin(combinationBoard.combinationBoardBookmarks, combinationBoardBookmark).on(combinationBoardBookmark.user.id.eq(userId))
                .where(combinationBoard.isActive.isTrue())
                .groupBy(combinationBoard.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        orderBySort(query, combinationSortCriteria);

        final JPAQuery<Long> count = jpaQueryFactory.select(combinationBoard.count())
                .from(combinationBoard)
                .where(combinationBoard.isActive.isTrue());

        return PageableExecutionUtils.getPage(query.fetch(), pageable, count::fetchOne);
    }

    private void orderBySort(final JPAQuery<?> query,
                             final CombinationSortCriteria combinationSortCriteria) {
        if (combinationSortCriteria.equals(CombinationSortCriteria.COMBINATION_LATEST_DESC)) {
            query.orderBy(combinationBoard.createdDate.desc());
        }

        if (combinationSortCriteria.equals(CombinationSortCriteria.COMBINATION_POPULAR_DESC)) {
            query.orderBy(
                    combinationBoard.combinationBoardLikes.size().add(combinationBoard.combinationBoardBookmarks.size()).desc(),
                    combinationBoard.createdDate.desc()
            );
        }
    }
}
