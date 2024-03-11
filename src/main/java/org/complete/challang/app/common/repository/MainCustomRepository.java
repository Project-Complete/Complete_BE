package org.complete.challang.app.common.repository;

import org.complete.challang.app.drink.controller.dto.response.DrinkListFindResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MainCustomRepository {

    Page<DrinkListFindResponse> findDrinksByKeyword(final String keyword,
                                                    final Long userId,
                                                    final Pageable pageable);
}
