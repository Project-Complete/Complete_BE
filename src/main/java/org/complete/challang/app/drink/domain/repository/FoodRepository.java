package org.complete.challang.app.drink.domain.repository;

import org.complete.challang.app.drink.domain.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {
}
