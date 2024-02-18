package org.complete.challang.drink.domain.repository;

import org.complete.challang.drink.controller.dto.response.DrinkListFindResponse;
import org.complete.challang.drink.domain.entity.Drink;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DrinkRepository extends JpaRepository<Drink, Long>, DrinkQueryRepository, JpaSpecificationExecutor<Drink> {

    Page<Drink> findAll(Specification<Drink> spec, Pageable pageable);

    @Query(value = "select new org.complete.challang.drink.controller.dto.response.DrinkListFindResponse(d.id, d.imageUrl, d.drinkManufacturer.manufacturerName, d.name, d.reviewSumRating/d.reviewCount) "
            + "from Drink d "
            + "left join d.reviews r "
            + "left join r.reviewFlavors rf "
            + "left join rf.flavor f "
            + "on f.flavor = :flavor "
            + "where d.id != :drinkId "
            + "group by d.id "
            + "order by count(f.flavor) desc")
    Page<DrinkListFindResponse> findDrinksOrderByMaxFlavor(@Param("flavor") final String flavor,
                                                           @Param("drinkId") final Long drinkId,
                                                           final Pageable pageable);
}
