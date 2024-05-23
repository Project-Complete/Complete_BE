package org.complete.challang.app.drink.domain.repository;

import org.complete.challang.app.drink.domain.entity.DrinkManufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DrinkManufacturerRepository extends JpaRepository<DrinkManufacturer, Long> {

    Optional<DrinkManufacturer> findByManufacturerName(String manufacturerName);
}
