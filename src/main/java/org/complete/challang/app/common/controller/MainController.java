package org.complete.challang.app.common.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.complete.challang.annotation.AuthUser;
import org.complete.challang.app.account.oauth2.CustomOAuth2User;
import org.complete.challang.app.common.controller.dto.response.SearchListFindResponse;
import org.complete.challang.app.common.service.MainService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Main", description = "Main 페이지 관련 API")
@RequiredArgsConstructor
@RequestMapping("/main")
@RestController
public class MainController {

    private final MainService mainService;

    @Operation(summary = "통합 검색", description = "주류 및 술 조합 검색 API")
    @GetMapping("/search")
    public ResponseEntity<SearchListFindResponse> findByKeyword(@RequestParam("keyword") final String keyword,
                                                                @RequestParam(value = "page", defaultValue = "1") final int page,
                                                                @AuthUser final CustomOAuth2User customOAuth2User) {
        final long userId = customOAuth2User.getUserId();
        final SearchListFindResponse searchResult = mainService.findByKeyword(keyword, userId, page);

        return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }
}
