package org.complete.challang.review.domain.repository;

import org.complete.challang.review.domain.entity.Flavor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlavorRepository extends JpaRepository<Flavor, Long> {
}
