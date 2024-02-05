package org.complete.challang.drink.repository;

import org.complete.challang.drink.domain.entity.Drink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrinkRepository extends JpaRepository<Drink, Long> {
}