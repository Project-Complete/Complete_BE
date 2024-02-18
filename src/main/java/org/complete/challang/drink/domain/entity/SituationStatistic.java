package org.complete.challang.drink.domain.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.complete.challang.review.controller.dto.item.SituationDto;
import org.complete.challang.review.domain.entity.Situation;

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

    public void updateSituationStatistic(final SituationDto situationDto) {
        if (situationDto.isAdult()) {
            adultSum++;
        }
        if (situationDto.isPartner()) {
            partnerSum++;
        }
        if (situationDto.isFriend()) {
            friendSum++;
        }
        if (situationDto.isBusiness()) {
            businessSum++;
        }
        if (situationDto.isAlone()) {
            aloneSum++;
        }
    }

    public void downgradeSituationStatistic(final Situation situation) {
        if (situation.isAdult()) {
            adultSum--;
        }
        if (situation.isPartner()) {
            partnerSum--;
        }
        if (situation.isFriend()) {
            friendSum--;
        }
        if (situation.isBusiness()) {
            businessSum--;
        }
        if (situation.isAlone()) {
            aloneSum--;
        }
    }
}
