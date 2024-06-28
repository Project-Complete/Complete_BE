package org.complete.challang.app.drink.domain.entity.criteria;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum DrinkSortCriteria {

    DRINK_SWEET_RATING_DESC("sweetSumRating", "tasteStatistic", "단맛순으로 정렬"),
    DRINK_BITTER_RATING_DESC("bitterSumRating", "tasteStatistic", "쓴순으로 정렬"),
    DRINK_SOUR_RATING_DESC("sourSumRating", "tasteStatistic", "신맛순으로 정렬"),
    DRINK_BODY_RATING_DESC("bodySumRating", "tasteStatistic", "바디감순으로 정렬"),
    DRINK_REFRESH_RATING_DESC("refreshSumRating", "tasteStatistic", "청량감순으로 정렬"),
    DRINK_ADULT_RATING_DESC("adultSum", "situationStatistic", "웃어른순으로 정렬"),
    DRINK_PARTNER_RATING_DESC("partnerSum", "situationStatistic", "연인순으로 정렬"),
    DRINK_ALONE_RATING_DESC("aloneSum", "situationStatistic", "혼자순으로 정렬"),
    DRINK_BUSINESS_RATING_DESC("businessSum", "situationStatistic", "비즈니스순으로 정렬"),
    DRINK_FRIEND_RATING_DESC("friendSum", "situationStatistic", "친구순으로 정렬"),

    DRINK_FRUITY_RATING_DESC("과일", null, "과일순으로 정렬"),
    DRINK_PLANT_RATING_DESC("식물", null, "식물순으로 정렬"),
    DRINK_GRAINY_RATING_DESC("곡물", null, "곡물순으로 정렬"),
    DRINK_FLORAL_RATING_DESC("꽃", null, "꽃순으로 정렬"),
    DRINK_DAIRY_RATING_DESC("유제품", null, "유제품순으로 정렬"),
    DRINK_SPICY_RATING_DESC("향신료", null, "향신료순으로 정렬"),
    DRINK_NUTTY_RATING_DESC("견과류", null, "견과류순으로 정렬"),
    DRINK_FERMENTED_RATING_DESC("발효", null, "발효순으로 정렬"),
    DRINK_SWEET_FLAVOR_RATING_DESC("달콤", null, "달콤순으로 정렬"),
    DRINK_ETC_RATING_DESC("기타", null, "기타순으로 정렬"),

    DRINK_POPULARITY_DESC("popularity", null, "인기순으로 정렬"),
    DRINK_LATEST_DESC("latest", null, "최신순으로 정렬"),
    DRINK_RANDOM_DESC("random", null, "랜덤순으로 정렬"),
    DRINK_REVIEW_DESC("review", null, "리뷰순으로 정렬");

    private final String value;
    private final String embeddedValue;
    private final String description;

    private static final Map<String, DrinkSortCriteria> BY_VALUE = new HashMap<>();

    static {
        for (DrinkSortCriteria e : values()) {
            BY_VALUE.put(e.value, e);
        }
    }

    public static DrinkSortCriteria getDrinkSortCriteria(String value) {
        return BY_VALUE.get(value);
    }
}
