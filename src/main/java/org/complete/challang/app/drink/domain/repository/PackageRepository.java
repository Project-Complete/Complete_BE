package org.complete.challang.app.drink.domain.repository;

import org.complete.challang.app.drink.domain.entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PackageRepository extends JpaRepository<Package, Long> {

    Optional<Package> findByTypeAndVolume(String type, String volume);
}
