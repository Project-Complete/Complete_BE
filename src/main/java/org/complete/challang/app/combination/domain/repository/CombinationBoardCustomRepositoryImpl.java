package org.complete.challang.app.combination.domain.repository;

import com.querydsl.core.types.OrderSpecifier;
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

import java.util.List;

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
        List<CombinationBoardListFindResponse> combinations = jpaQueryFactory.select(
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
                .orderBy(orderBySort(combinationSortCriteria))
                .groupBy(combinationBoard.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        final JPAQuery<Long> count = jpaQueryFactory.select(combinationBoard.count())
                .from(combinationBoard)
                .where(combinationBoard.isActive.isTrue());

        return PageableExecutionUtils.getPage(combinations, pageable, count::fetchOne);
    }

    private OrderSpecifier<?> orderBySort(CombinationSortCriteria combinationSortCriteria) {
        if (combinationSortCriteria.equals(CombinationSortCriteria.COMBINATION_LATEST_DESC)) {
            return combinationBoard.createdDate.desc();
        }
        return null;
    }
}
