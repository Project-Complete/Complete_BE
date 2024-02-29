package org.complete.challang.app.drink.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.complete.challang.app.common.exception.SuccessResponse;
import org.complete.challang.app.drink.controller.dto.request.DrinkCreateRequest;
import org.complete.challang.app.drink.controller.dto.response.*;
import org.complete.challang.app.drink.service.DrinkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Drink", description = "주류 관련 API")
@RequiredArgsConstructor
@RequestMapping("/drinks")
@RestController
public class DrinkController {

    private final DrinkService drinkService;

    @Operation(summary = "주류 상세 조회", description = "주류 단건 상세 조회")
    @GetMapping("/detail/{drink_id}")
    public ResponseEntity<DrinkFindResponse> findDetailDrink(@PathVariable("drink_id") final Long drinkId,
                                                             @AuthenticationPrincipal final UserDetails userDetails) {

        return new ResponseEntity<>(drinkService.findDetailDrink(drinkId, userDetails), HttpStatus.OK);
    }

    @Operation(summary = "주류 평가 리스트 조회", description = "주류 평가(맛/향/상황)별 추천 리스트 조회")
    @GetMapping("/{drink_id}/search")
    public ResponseEntity<DrinkPageResponse<DrinkListFindResponse>> findRateDrinks(@PathVariable("drink_id") final Long drinkId,
                                                                                   @RequestParam("rate") final String rate,
                                                                                   @AuthenticationPrincipal final UserDetails userDetails) {

        return new ResponseEntity<>(drinkService.findRateDrinks(drinkId, rate, userDetails), HttpStatus.OK);
    }

    @Operation(summary = "주류 타입별 리스트 조회", description = "주류 타입(맥주/전통주)별 및 정렬순 리스트 조회")
    @GetMapping("/search")
    public ResponseEntity<DrinkPageResponse<DrinkListFindResponse>> findDrinks(@RequestParam("drink_type") final String drinkType,
                                                                               @RequestParam("sorted") final String sorted,
                                                                               @RequestParam(value = "page", defaultValue = "1") final int page,
                                                                               @AuthenticationPrincipal final UserDetails userDetails) {

        return new ResponseEntity<>(drinkService.findDrinks(drinkType, sorted, page, userDetails), HttpStatus.OK);
    }

    @Operation(summary = "주류 좋아요", description = "유저 주류 좋아요 추가")
    @PostMapping("/{drink_id}/like")
    public ResponseEntity<SuccessResponse> createLikeDrink(@PathVariable("drink_id") final Long drinkId,
                                                           @AuthenticationPrincipal final UserDetails userDetails) {

        return new ResponseEntity<>(drinkService.likeDrink(drinkId, userDetails), HttpStatus.CREATED);
    }

    @Operation(summary = "주류 좋아요 삭제", description = "유저 주류 좋아요 삭제")
    @DeleteMapping("/{drink_id}/like")
    public ResponseEntity<SuccessResponse> deleteLikeDrink(@PathVariable("drink_id") final Long drinkId,
                                                           @AuthenticationPrincipal final UserDetails userDetails) {

        return new ResponseEntity<>(drinkService.unLikeDrink(drinkId, userDetails), HttpStatus.OK);
    }

    @Operation(summary = "메인 페이지 주류 배너", description = "메인 페이지 배너 4가지 조회")
    @GetMapping("/banner")
    public ResponseEntity<DrinkPageResponse<DrinkBannerListFindResponse>> findDrinksForBanner() {

        return new ResponseEntity<>(drinkService.findDrinksForBanner(), HttpStatus.OK);
    }

    @Operation(summary = "주류 추가", description = "주류 추가 (관리자 기능)")
    @PostMapping
    public ResponseEntity<DrinkCreateResponse> createDrink(@RequestBody final DrinkCreateRequest drinkCreateRequest) {

        return new ResponseEntity<>(drinkService.createDrink(drinkCreateRequest), HttpStatus.OK);
    }
}
