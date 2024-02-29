package org.complete.challang.app.review.controller.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.*;
import lombok.*;
import org.complete.challang.app.account.user.domain.entity.User;
import org.complete.challang.app.drink.domain.entity.Drink;
import org.complete.challang.app.review.controller.dto.item.SituationDto;
import org.complete.challang.app.review.controller.dto.item.TasteDto;
import org.complete.challang.app.review.domain.entity.Review;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReviewCreateRequest {

    @NotNull
    private Long drinkId;

    @NotBlank
    private String imageUrl;

    @NotBlank
    @Size(min = 20, max = 250)
    private String content;

    @NotNull
    @Min(0)
    @Max(5)
    private float rating;

    private SituationDto situationDto;
    private TasteDto tasteDto;
    private List<Long> flavors;
    private List<Long> foods;

    public Review toEntity(final Drink drink,
                           final User user) {
        return Review.builder()
                .imageUrl(imageUrl)
                .rating(rating)
                .content(content)
                .situation(situationDto.toEntity())
                .taste(tasteDto.toEntity())
                .drink(drink)
                .user(user)
                .build();
    }
}
