package org.complete.challang.drink.domain.repository;

import org.complete.challang.drink.domain.entity.Drink;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DrinkRepository extends JpaRepository<Drink, Long>, DrinkQueryRepository, JpaSpecificationExecutor<Drink> {

    Page<Drink> findAll(Specification<Drink> spec, Pageable pageable);
}
