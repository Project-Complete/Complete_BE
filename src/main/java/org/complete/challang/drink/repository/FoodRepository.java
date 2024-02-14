package org.complete.challang.drink.repository;

import org.complete.challang.drink.domain.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {
}
