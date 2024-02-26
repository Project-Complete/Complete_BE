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

import java.util.Optional;

public interface DrinkRepository extends JpaRepository<Drink, Long>, DrinkQueryRepository, JpaSpecificationExecutor<Drink>, DrinkCustomRepository {

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

    Optional<Drink> findByIdAndIsActiveTrue(Long drinkId);

    @Query("SELECT d FROM Drink d WHERE "
            + "(:drinkType = '전체' OR d.drinkDetailType.drinkType.drinkType = :drinkType) "
            + "ORDER BY SIZE(d.drinkLikes) DESC")
    Page<Drink> findDrinksByTypeOrderByLikes(@Param("drinkType") String drinkType,
                                             Pageable pageable);

    @Query("SELECT count(d) > 0 FROM Drink d "
            + "JOIN d.drinkLikes dl "
            + "JOIN dl.user u "
            + "WHERE u.id = :userId AND d.id = :drinkId")
    boolean existByDrinkLike(@Param("userId") Long userId,
                             @Param("drinkId") Long drinkId);
}
