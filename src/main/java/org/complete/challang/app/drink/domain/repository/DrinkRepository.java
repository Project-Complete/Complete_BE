package org.complete.challang.app.drink.domain.repository;

import org.complete.challang.app.drink.controller.dto.response.DrinkListFindResponse;
import org.complete.challang.app.drink.domain.entity.Drink;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DrinkRepository extends JpaRepository<Drink, Long>, DrinkQueryRepository, JpaSpecificationExecutor<Drink>, DrinkCustomRepository {

    Page<Drink> findAll(final Specification<Drink> spec, final Pageable pageable);

    @Query(value = "select new org.complete.challang.app.drink.controller.dto.response.DrinkListFindResponse(d.id, d.imageUrl, d.drinkManufacturer.manufacturerName, count(u.id) > 0, d.name, "
            + "case when d.reviewCount = 0 then 0 else d.reviewSumRating/d.reviewCount end) "
            + "from Drink d "
            + "left join d.reviews r "
            + "left join r.reviewFlavors rf "
            + "left join rf.flavor f "
            + "on f.flavor = :flavor "
            + "left join d.drinkLikes dl "
            + "left join dl.user u "
            + "on u.id = :userId "
            + "where d.id != :drinkId and d.isActive = true "
            + "group by d.id "
            + "order by count(f.flavor) desc")
    Page<DrinkListFindResponse> findDrinksOrderByMaxFlavor(@Param("flavor") final String flavor,
                                                           @Param("drinkId") final Long drinkId,
                                                           final Pageable pageable,
                                                           @Param("userId") final Long userId);

    Optional<Drink> findByIdAndIsActiveTrue(Long drinkId);

    @Query("SELECT d FROM Drink d WHERE "
            + "(:drinkType = '전체' OR d.drinkDetailType.drinkType.drinkType = :drinkType) "
            + "ORDER BY SIZE(d.drinkLikes) DESC")
    Page<Drink> findDrinksByTypeOrderByLikes(@Param("drinkType") final String drinkType,
                                             final Pageable pageable);

    @Query("SELECT count(d) > 0 FROM Drink d "
            + "JOIN d.drinkLikes dl "
            + "JOIN dl.user u "
            + "WHERE u.id = :userId AND d.id = :drinkId")
    boolean existByDrinkLike(@Param("userId") final Long userId,
                             @Param("drinkId") final Long drinkId);
}
