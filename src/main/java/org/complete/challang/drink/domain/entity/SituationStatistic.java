package org.complete.challang.drink.domain.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class SituationStatistic {

    private long adultSum;

    private long partnerSum;

    private long friendSum;

    private long businessSum;

    private long aloneSum;
}