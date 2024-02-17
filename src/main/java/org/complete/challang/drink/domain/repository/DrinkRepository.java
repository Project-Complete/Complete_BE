package org.complete.challang.drink.domain.repository;

import org.complete.challang.drink.domain.entity.Drink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DrinkRepository extends JpaRepository<Drink, Long>, DrinkQueryRepository {

    Optional<Drink> findByIdAndIsActiveTrue(Long drinkId);
}
