package org.complete.challang.app.combination.controller.dto.item;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import org.complete.challang.app.combination.domain.entity.Combination;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EtcCombinationFindDto {

    private String name;
    private String volume;

    @JsonProperty("xcoordinate")
    private int xCoordinate;

    @JsonProperty("ycoordinate")
    private int yCoordinate;

    public static EtcCombinationFindDto toDto(Combination combination) {
        return EtcCombinationFindDto.builder()
                .name(combination.getName())
                .volume(combination.getVolume())
                .xCoordinate(combination.getXCoordinate())
                .yCoordinate(combination.getYCoordinate())
                .build();
    }
}
