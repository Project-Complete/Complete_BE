package org.complete.challang.app.review.domain.repository;

import org.complete.challang.app.review.domain.entity.Situation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SituationRepository extends JpaRepository<Situation, Long> {
}
