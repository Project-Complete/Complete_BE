package org.complete.challang.app.drink.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.*;
import org.complete.challang.app.review.controller.dto.item.TasteDto;
import org.complete.challang.app.review.domain.entity.Taste;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Embeddable
public class TasteStatistic {

    @ColumnDefault("0")
    private double sweetSumRating;

    @ColumnDefault("0")
    private double sourSumRating;

    @ColumnDefault("0")
    private double bitterSumRating;

    @ColumnDefault("0")
    private double bodySumRating;

    @ColumnDefault("0")
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
