package org.complete.challang.app.common.repository;

import org.complete.challang.app.combination.controller.dto.response.CombinationBoardListFindResponse;
import org.complete.challang.app.combination.domain.entity.CombinationSortCriteria;
import org.complete.challang.app.drink.controller.dto.response.DrinkListFindResponse;
import org.complete.challang.app.drink.domain.entity.criteria.DrinkSortCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchCustomRepository {

    Page<DrinkListFindResponse> findDrinksByKeyword(final String keyword,
                                                    final Long userId,
                                                    final DrinkSortCriteria drinkSortCriteria,
                                                    final Pageable pageable);

    Page<CombinationBoardListFindResponse> findCombinationBoardsByKeyword(final String keyword,
                                                                          final Long userId,
                                                                          final CombinationSortCriteria combinationSortCriteria,
                                                                          final Pageable pageable);

    Page<CombinationBoardListFindResponse> findCombinationBoardsByDrinkId(final Long drinkId,
                                                                          final Long userId,
                                                                          final CombinationSortCriteria combinationSortCriteria,
                                                                          final Pageable pageable);
}
