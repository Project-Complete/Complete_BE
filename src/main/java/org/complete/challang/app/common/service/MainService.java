package org.complete.challang.app.common.service;

import lombok.RequiredArgsConstructor;
import org.complete.challang.app.common.controller.dto.response.SearchListFindResponse;
import org.complete.challang.app.common.repository.MainCustomRepository;
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
public class MainService {

    private final MainCustomRepository mainCustomRepository;

    public SearchListFindResponse findByKeyword(final String keyword,
                                                final Long userId,
                                                final int page) {
        final Page<DrinkListFindResponse> drinkListFindResponses = mainCustomRepository.findDrinksByKeyword(keyword, userId, PageRequest.of(page - 1, 8));
        final DrinkSortCriteria drinkSortCriteria = DrinkSortCriteria.getDrinkSortCriteria("latest_order");
        final DrinkPageResponse<DrinkListFindResponse> drinks = DrinkPageResponse.toDto(
                drinkListFindResponses.getContent(),
                drinkListFindResponses,
                drinkSortCriteria.getDescription()
        );

        return SearchListFindResponse.toDto(drinks);
    }
}
