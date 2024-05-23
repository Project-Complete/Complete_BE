package org.complete.challang.app.review.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum ReviewSortCriteria {

    REVIEW_CREATED_DATE_DESC("latest", "최신순으로 정렬", Sort.by("createdDate").descending()),
    ;

    private final String value;
    private final String description;
    private final Sort sortCriteria;

    private static final Map<String, ReviewSortCriteria> BY_VALUE = new HashMap<>();

    static {
        for (ReviewSortCriteria e : values()) {
            BY_VALUE.put(e.value, e);
        }
    }

    public static Sort sortCriteriaOfValue(String value) {
        return BY_VALUE.get(value).sortCriteria;
    }
}
