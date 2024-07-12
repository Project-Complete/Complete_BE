package org.complete.challang.app.combination.domain.repository;

import org.complete.challang.app.combination.controller.dto.response.CombinationBoardListFindResponse;
import org.complete.challang.app.combination.domain.entity.CombinationSortCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CombinationBoardCustomRepository {

    Page<CombinationBoardListFindResponse> findAllBySorted(final CombinationSortCriteria combinationSortCriteria,
                                                           final Pageable pageable,
                                                           final Long userId);

    Page<CombinationBoardListFindResponse> findAllByUser(final Long userId,
                                                         final CombinationSortCriteria combinationSortCriteria,
                                                         final Pageable pageable);

    Page<CombinationBoardListFindResponse> findAllByUserLike(final Long userId,
                                                             final CombinationSortCriteria combinationSortCriteria,
                                                             final Pageable pageable);
}
