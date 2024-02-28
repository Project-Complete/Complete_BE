package org.complete.challang.app.review.domain.repository;

import org.complete.challang.app.review.domain.entity.Flavor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlavorRepository extends JpaRepository<Flavor, Long> {
}
