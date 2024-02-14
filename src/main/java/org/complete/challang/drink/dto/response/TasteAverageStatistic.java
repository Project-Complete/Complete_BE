package org.complete.challang.drink.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TasteAverageStatistic {

    private double sweetRating;

    private double sourRating;

    private double bitterRating;

    private double bodyRating;

    private double refreshRating;
}
