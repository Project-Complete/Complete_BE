package org.complete.challang.drink.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum DrinkSortCriteria {

    DRINK_SWEET_SUM_DESC("sweetSumRating", "단맛순으로 정렬", Sort.by("tasteStatistic.sweetSumRating").descending());

    private final String value;
    private final String description;
    private final Sort sortCriteria;

    private static final Map<String, DrinkSortCriteria> BY_VALUE = new HashMap<>();

    static {
        for (DrinkSortCriteria e : values()) {
            BY_VALUE.put(e.value, e);
        }
    }

    public static Sort sortCriteriaOfValue(String value) {
        return BY_VALUE.get(value).sortCriteria;
    }
}
