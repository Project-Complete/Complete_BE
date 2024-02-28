package org.complete.challang.app.drink.domain.entity.criteria;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public enum DrinkTypeCriteria {

    ALL("all", "전체"),
    BEER("beer", "맥주"),
    TRADITION("tradition", "전통주");

    private static final Map<String, String> BY_VALUE = new HashMap<>();

    static {
        for (DrinkTypeCriteria drinkTypeCriteria : values()) {
            BY_VALUE.put(drinkTypeCriteria.type, drinkTypeCriteria.physicalType);
        }
    }

    private final String type;
    private final String physicalType;

    public static String getPhysicalType(String type) {
        return BY_VALUE.get(type);
    }
}
