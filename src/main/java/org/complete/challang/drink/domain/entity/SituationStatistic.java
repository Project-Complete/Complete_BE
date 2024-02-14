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
public class SituationStatistic {

    private long adultSum;

    private long partnerSum;

    private long friendSum;

    private long businessSum;

    private long aloneSum;

    public void updateSituationStatistic(final boolean adult,
                                         final boolean partner,
                                         final boolean friend,
                                         final boolean business,
                                         final boolean alone) {
        if (adult) {
            adultSum++;
        }
        if (partner) {
            partnerSum++;
        }
        if (friend) {
            friendSum++;
        }
        if (business) {
            businessSum++;
        }
        if (alone) {
            aloneSum++;
        }
    }
}
