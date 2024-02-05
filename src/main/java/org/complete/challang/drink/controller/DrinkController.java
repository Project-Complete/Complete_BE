package org.complete.challang.drink.controller;

import lombok.RequiredArgsConstructor;
import org.complete.challang.drink.repository.DrinkFindResponse;
import org.complete.challang.drink.service.DrinkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/drink")
public class DrinkController {

    private final DrinkService drinkService;

    @GetMapping("/detail/{drink_id}")
    public ResponseEntity<DrinkFindResponse> findDetailDrink(@PathVariable("drink_id") Long drinkId){
        return new ResponseEntity<>(drinkService.findDetailDrink(drinkId), HttpStatus.OK);
    }
}
