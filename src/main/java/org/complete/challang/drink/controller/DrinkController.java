package org.complete.challang.drink.controller;

import lombok.RequiredArgsConstructor;
import org.complete.challang.common.exception.SuccessResponse;
import org.complete.challang.drink.controller.dto.response.DrinkFindResponse;
import org.complete.challang.drink.controller.dto.response.DrinkListFindResponse;
import org.complete.challang.drink.controller.dto.response.DrinkPageResponse;
import org.complete.challang.drink.service.DrinkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/drink")
public class DrinkController {

    private final DrinkService drinkService;

    @GetMapping("/detail/{drink_id}")
    public ResponseEntity<DrinkFindResponse> findDetailDrink(@PathVariable("drink_id") Long drinkId) {
        return new ResponseEntity<>(drinkService.findDetailDrink(drinkId), HttpStatus.OK);
    }

    @GetMapping("/{drink_id}/search")
    public ResponseEntity<DrinkPageResponse<DrinkListFindResponse>> findRateDrinks(@PathVariable("drink_id") Long drinkId,
                                                                                   @RequestParam("rate") String rate) {
        return new ResponseEntity<>(drinkService.findRateDrinks(drinkId, rate), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<DrinkPageResponse<DrinkListFindResponse>> findDrinks(@RequestParam("drink_type") String drinkType,
                                                                               @RequestParam("sorted") String sorted,
                                                                               @RequestParam(value = "page", defaultValue = "1") int page) {
        return new ResponseEntity<>(drinkService.findDrinks(drinkType, sorted, page), HttpStatus.OK);
    }

    @PostMapping("/{drink_id}/like")
    public ResponseEntity<SuccessResponse> createLikeDrink(@PathVariable("drink_id") Long drinkId,
                                                           @AuthenticationPrincipal UserDetails userDetails) {
        return new ResponseEntity<>(drinkService.likeDrink(drinkId, userDetails), HttpStatus.CREATED);
    }

    @DeleteMapping("/{drink_id}/like")
    public ResponseEntity<SuccessResponse> deleteLikeDrink(@PathVariable("drink_id") Long drinkId,
                                                           @AuthenticationPrincipal UserDetails userDetails) {
        return new ResponseEntity<>(drinkService.unLikeDrink(drinkId, userDetails), HttpStatus.OK);
    }
}
