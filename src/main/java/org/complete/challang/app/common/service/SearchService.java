package org.complete.challang.app.common.service;

import lombok.RequiredArgsConstructor;
import org.complete.challang.app.combination.controller.dto.response.CombinationBoardListFindResponse;
import org.complete.challang.app.combination.controller.dto.response.CombinationBoardPageResponse;
import org.complete.challang.app.combination.domain.entity.CombinationSortCriteria;
import org.complete.challang.app.common.repository.SearchCustomRepository;
import org.complete.challang.app.drink.controller.dto.response.DrinkListFindResponse;
import org.complete.challang.app.drink.controller.dto.response.DrinkPageResponse;
import org.complete.challang.app.drink.domain.entity.criteria.DrinkSortCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SearchService {

    private final SearchCustomRepository searchCustomRepository;

    public DrinkPageResponse<DrinkListFindResponse> findDrinksByKeyword(final String keyword,
                                                                        final Long userId,
                                                                        final String sorted,
                                                                        final int page) {
        final DrinkSortCriteria drinkSortCriteria = DrinkSortCriteria.getDrinkSortCriteria(sorted);
        final Page<DrinkListFindResponse> drinkListFindResponses = searchCustomRepository.findDrinksByKeyword(
                keyword,
                userId,
                drinkSortCriteria,
                PageRequest.of(page - 1, 8)
        );

        return DrinkPageResponse.toDto(
                drinkListFindResponses.getContent(),
                drinkListFindResponses,
                drinkSortCriteria.getDescription()
        );
    }

    public CombinationBoardPageResponse<CombinationBoardListFindResponse> findCombinationBoardsByKeyword(final String keyword,
                                                                                                         final Long userId,
                                                                                                         final String sorted,
                                                                                                         final int page) {
        final CombinationSortCriteria combinationSortCriteria = CombinationSortCriteria.getCombinationSortCriteria(sorted);
        final Page<CombinationBoardListFindResponse> combinationBoardListFindResponses = searchCustomRepository.findCombinationBoardsByKeyword(
                keyword,
                userId,
                combinationSortCriteria,
                PageRequest.of(page - 1, 8)
        );

        return CombinationBoardPageResponse.toDto(
                combinationBoardListFindResponses.getContent(),
                combinationBoardListFindResponses,
                combinationSortCriteria.getDescription()
        );
    }

    public CombinationBoardPageResponse<CombinationBoardListFindResponse> findCombinationBoardsByDrinkId(final Long drinkId,
                                                                                                         final Long userId,
                                                                                                         final String sorted,
                                                                                                         final int page) {
        final CombinationSortCriteria combinationSortCriteria = CombinationSortCriteria.getCombinationSortCriteria(sorted);
        Page<CombinationBoardListFindResponse> combinationBoardListFindResponses = searchCustomRepository.findCombinationBoardsByDrinkId(
                drinkId,
                userId,
                combinationSortCriteria,
                PageRequest.of(page - 1, 8)
        );

        return CombinationBoardPageResponse.toDto(
                combinationBoardListFindResponses.getContent(),
                combinationBoardListFindResponses,
                combinationSortCriteria.getDescription()
        );
    }
}
