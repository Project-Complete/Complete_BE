package org.complete.challang.app.drink.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.*;
import org.complete.challang.app.review.controller.dto.item.TasteDto;
import org.complete.challang.app.review.domain.entity.Taste;

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

    public void updateTasteRating(final TasteDto tasteDto) {
        sweetSumRating += tasteDto.getSweet();
        sourSumRating += tasteDto.getSour();
        bitterSumRating += tasteDto.getBitter();
        bodySumRating += tasteDto.getBody();
        refreshSumRating += tasteDto.getRefresh();
    }

    public void downgradeTasteRating(final Taste taste) {
        sweetSumRating -= taste.getSweet();
        sourSumRating -= taste.getSour();
        bitterSumRating -= taste.getBitter();
        bodySumRating -= taste.getBody();
        refreshSumRating -= taste.getRefresh();
    }
}
