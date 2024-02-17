package org.complete.challang.drink.domain.entity.criteria;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum DrinkSortCriteria {

    DRINK_SWEET_RATING_DESC("sweetSumRating", "tasteStatistic", "단맛순으로 정렬", null),
    DRINK_BITTER_RATING_DESC("bitterSumRating", "tasteStatistic", "쓴순으로 정렬", null),
    DRINK_SOUR_RATING_DESC("sourSumRating", "tasteStatistic", "신맛순으로 정렬", null),
    DRINK_BODY_RATING_DESC("bodySumRating", "tasteStatistic", "바디감순으로 정렬", null),
    DRINK_REFRESH_RATING_DESC("refreshSumRating", "tasteStatistic", "청량감순으로 정렬", null),
    DRINK_ADULT_RATING_DESC("adultSum", "situationStatistic", "웃어른순으로 정렬", null),
    DRINK_PARTNER_RATING_DESC("partnerSum", "situationStatistic", "연인순으로 정렬", null),
    DRINK_ALONE_RATING_DESC("aloneSum", "situationStatistic", "혼자순으로 정렬", null),
    DRINK_BUSINESS_RATING_DESC("businessSum", "situationStatistic", "비즈니스순으로 정렬", null),
    DRINK_FRIEND_RATING_DESC("friendSum", "situationStatistic", "친구순으로 정렬", null);


    private final String value;
    private final String embeddedValue;
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

    public static DrinkSortCriteria getDrinkSortCriteria(String value) {
        return BY_VALUE.get(value);
    }
}
