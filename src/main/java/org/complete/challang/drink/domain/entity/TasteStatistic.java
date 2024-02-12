package org.complete.challang.drink.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Embeddable
public class TasteStatistic {

    private double sweetSumRating;

    private double sourSumRating;

    private double bitterSumRating;

    private double bodySumRating;

    private double refreshSumRating;
}
