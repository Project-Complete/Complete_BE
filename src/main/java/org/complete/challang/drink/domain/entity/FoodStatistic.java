package org.complete.challang.drink.domain.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class FoodStatistic {

    private long meat;

    private long processedMeat;

    private long seafood;

    private long nuts;

    private long fruit;

    private long cheese;

    private long snack;

    private long driedSnack;

    private long fried;

    private long soup;

    private long spicy;

    private long western;
}
