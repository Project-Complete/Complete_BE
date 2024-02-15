package org.complete.challang.drink.domain.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Embeddable
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
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
