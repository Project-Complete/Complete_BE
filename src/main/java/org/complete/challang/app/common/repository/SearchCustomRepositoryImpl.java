package org.complete.challang.app.common.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.complete.challang.app.combination.controller.dto.response.CombinationBoardListFindResponse;
import org.complete.challang.app.combination.domain.entity.CombinationSortCriteria;
import org.complete.challang.app.drink.controller.dto.response.DrinkListFindResponse;
import org.complete.challang.app.drink.domain.entity.criteria.DrinkSortCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.complete.challang.app.account.user.domain.entity.QDrinkLike.drinkLike;
import static org.complete.challang.app.account.user.domain.entity.QUser.user;
import static org.complete.challang.app.combination.domain.entity.QCombination.combination;
import static org.complete.challang.app.combination.domain.entity.QCombinationBoard.combinationBoard;
import static org.complete.challang.app.combination.domain.entity.QCombinationBoardBookmark.combinationBoardBookmark;
import static org.complete.challang.app.combination.domain.entity.QCombinationBoardLike.combinationBoardLike;
import static org.complete.challang.app.drink.domain.entity.QDrink.drink;
import static org.complete.challang.app.util.QueryUtils.getReviewRating;

@RequiredArgsConstructor
@Repository
public class SearchCustomRepositoryImpl implements SearchCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<DrinkListFindResponse> findDrinksByKeyword(final String keyword,
                                                           final Long userId,
                                                           final DrinkSortCriteria drinkSortCriteria,
                                                           final Pageable pageable) {
        final List<DrinkListFindResponse> drinks = jpaQueryFactory.select(
                        Projections.constructor(
                                DrinkListFindResponse.class,
                                drink.id,
                                drink.imageUrl,
                                drink.drinkManufacturer.manufacturerName,
                                drinkLike.count().eq(1L),
                                drink.name,
                                getReviewRating()
                        )
                )
                .from(drink)
                .leftJoin(drink.drinkLikes, drinkLike).on(drinkLike.user.id.eq(userId))
                .where(getLike(drink.name, keyword))
                .where(drink.isActive.isTrue())
                .orderBy(orderDrinkByCriteria(drinkSortCriteria))
                .groupBy(drink.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        final JPAQuery<Long> count = jpaQueryFactory.select(drink.id.count())
                .from(drink)
                .where(drink.isActive.isTrue())
                .where(getLike(drink.name, keyword));

        return PageableExecutionUtils.getPage(drinks, pageable, count::fetchOne);
    }

    @Override
    public Page<CombinationBoardListFindResponse> findCombinationBoardsByKeyword(final String keyword,
                                                                                 final Long userId,
                                                                                 final CombinationSortCriteria combinationSortCriteria,
                                                                                 final Pageable pageable) {
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
                .leftJoin(combinationBoard.user, user)
                .leftJoin(combinationBoard.combinationBoardLikes, combinationBoardLike).on(combinationBoardLike.user.id.eq(userId))
                .leftJoin(combinationBoard.combinationBoardBookmarks, combinationBoardBookmark).on(combinationBoardBookmark.user.id.eq(userId))
                .where(getLike(combinationBoard.title, keyword))
                .where(combinationBoard.isActive.isTrue())
                .groupBy(combinationBoard.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        orderCombinationBoardByCriteria(query, combinationSortCriteria);

        final JPAQuery<Long> count = jpaQueryFactory.select(combinationBoard.count())
                .from(combinationBoard)
                .where(combinationBoard.isActive.isTrue())
                .where(getLike(combinationBoard.title, keyword));

        return PageableExecutionUtils.getPage(query.fetch(), pageable, count::fetchOne);
    }

    @Override
    public Page<CombinationBoardListFindResponse> findCombinationBoardsByDrinkId(final Long drinkId,
                                                                                 final Long userId,
                                                                                 final CombinationSortCriteria combinationSortCriteria,
                                                                                 final Pageable pageable) {
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
                .from(combination)
                .join(combination.combinationBoard, combinationBoard)
                .join(combinationBoard.user, user)
                .leftJoin(combinationBoard.combinationBoardLikes, combinationBoardLike).on(combinationBoardLike.user.id.eq(userId))
                .leftJoin(combinationBoard.combinationBoardBookmarks, combinationBoardBookmark).on(combinationBoardBookmark.user.id.eq(userId))
                .where(combination.drink.id.eq(drinkId))
                .where(combination.isActive.isTrue())
                .groupBy(combinationBoard.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        orderCombinationBoardByCriteria(query, combinationSortCriteria);

        final JPAQuery<Long> count = jpaQueryFactory.select(combination.count())
                .from(combination)
                .where(combination.drink.id.eq(drinkId))
                .where(combination.isActive.isTrue());

        return PageableExecutionUtils.getPage(query.fetch(), pageable, count::fetchOne);
    }

    private static BooleanExpression getLike(StringPath path, String keyword) {
        return Expressions.stringTemplate("replace({0}, ' ', '')", path)
                .lower()
                .like("%" + keyword.replace(" ", "").toLowerCase() + "%");
    }

    private OrderSpecifier<?> orderDrinkByCriteria(final DrinkSortCriteria drinkSortCriteria) {
        return switch (drinkSortCriteria) {
            case DRINK_LATEST_DESC -> drink.createdDate.desc();
            case DRINK_POPULARITY_DESC -> drink.drinkLikes.size().desc();
            case DRINK_REVIEW_DESC -> drink.reviewCount.desc();
            default -> null;
        };
    }

    private void orderCombinationBoardByCriteria(final JPAQuery<?> query,
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
