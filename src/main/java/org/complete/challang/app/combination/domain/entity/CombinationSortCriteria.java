package org.complete.challang.app.combination.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum CombinationSortCriteria {

    COMBINATION_LATEST_DESC("latest", "최신순으로 정렬"),
    COMBINATION_POPULAR_DESC("popularity", "인기순으로 정렬");

    private final String value;
    private final String description;

    private static final Map<String, CombinationSortCriteria> BY_VALUE = new HashMap<>();

    static {
        for (CombinationSortCriteria e : values()) {
            BY_VALUE.put(e.value, e);
        }
    }

    public static CombinationSortCriteria getCombinationSortCriteria(String value) {
        return BY_VALUE.get(value);
    }
}
