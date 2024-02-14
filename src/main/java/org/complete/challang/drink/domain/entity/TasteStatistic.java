package org.complete.challang.drink.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

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

    public void updateTasteRating(final double sweetRating,
                                  final double sourRating,
                                  final double bitterRating,
                                  final double bodyRating,
                                  double refreshRating) {
        sweetSumRating += sweetRating;
        sourSumRating += sourRating;
        bitterSumRating += bitterRating;
        bodySumRating += bodyRating;
        refreshSumRating += refreshRating;
    }
}
