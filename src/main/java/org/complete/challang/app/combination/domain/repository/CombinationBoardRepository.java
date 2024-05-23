package org.complete.challang.app.combination.domain.repository;

import org.complete.challang.app.combination.domain.entity.CombinationBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CombinationBoardRepository extends JpaRepository<CombinationBoard, Long>, CombinationBoardCustomRepository {
}
