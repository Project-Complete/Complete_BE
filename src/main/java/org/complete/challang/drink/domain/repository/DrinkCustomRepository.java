package org.complete.challang.drink.domain.repository;

import org.complete.challang.drink.controller.dto.response.DrinkListFindResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DrinkCustomRepository {

    Page<DrinkListFindResponse> findAllByType(String type,
                                              String sorted,
                                              Pageable pageable);
}
