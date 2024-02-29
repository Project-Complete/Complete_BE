package org.complete.challang.app.drink.domain.repository;

import org.complete.challang.app.drink.controller.dto.response.DrinkBannerListFindResponse;
import org.complete.challang.app.drink.controller.dto.response.DrinkListFindResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DrinkCustomRepository {

    Page<DrinkListFindResponse> findAllByType(final String type,
                                              final String sorted,
                                              final Pageable pageable,
                                              final Long userId);

    Page<DrinkBannerListFindResponse> findForBanner();
}
