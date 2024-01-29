package org.complete.challang.drink.domain.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class TasteStatistic {

    private double sweetSumRating;

    private double sourSumRating;

    private double bitterSumRating;

    private double bodySumRating;

    private double refreshSumRating;
}
