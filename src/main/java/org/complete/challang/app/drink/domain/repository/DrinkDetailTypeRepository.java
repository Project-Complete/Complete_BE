package org.complete.challang.app.drink.domain.repository;

import org.complete.challang.app.drink.domain.entity.DrinkDetailType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DrinkDetailTypeRepository extends JpaRepository<DrinkDetailType, Long> {

    Optional<DrinkDetailType> findByDetailType(String detailType);
}
