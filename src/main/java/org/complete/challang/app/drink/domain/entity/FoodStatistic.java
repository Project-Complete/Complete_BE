package org.complete.challang.app.drink.domain.entity;

import jakarta.persistence.Embeddable;
import org.hibernate.annotations.ColumnDefault;

@Embeddable
public class FoodStatistic {

    @ColumnDefault("0")
    private long meat;

    @ColumnDefault("0")
    private long processedMeat;

    @ColumnDefault("0")
    private long seafood;

    @ColumnDefault("0")
    private long nuts;

    @ColumnDefault("0")
    private long fruit;

    @ColumnDefault("0")
    private long cheese;

    @ColumnDefault("0")
    private long snack;

    @ColumnDefault("0")
    private long driedSnack;

    @ColumnDefault("0")
    private long fried;

    @ColumnDefault("0")
    private long soup;

    @ColumnDefault("0")
    private long spicy;

    @ColumnDefault("0")
    private long western;
}
