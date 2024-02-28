package org.complete.challang.app.review.domain.repository;

import org.complete.challang.app.review.domain.entity.Taste;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TasteRepository extends JpaRepository<Taste, Long> {
}
