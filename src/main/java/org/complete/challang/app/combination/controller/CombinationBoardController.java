package org.complete.challang.app.combination.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.complete.challang.annotation.AuthUser;
import org.complete.challang.annotation.IdValid;
import org.complete.challang.app.account.oauth2.CustomOAuth2User;
import org.complete.challang.app.combination.controller.dto.request.CombinationBoardCreateRequest;
import org.complete.challang.app.combination.controller.dto.request.CombinationBoardUpdateRequest;
import org.complete.challang.app.combination.controller.dto.response.CombinationBoardCreateUpdateResponse;
import org.complete.challang.app.combination.controller.dto.response.CombinationBoardFindResponse;
import org.complete.challang.app.combination.controller.dto.response.CombinationBoardListFindResponse;
import org.complete.challang.app.combination.controller.dto.response.CombinationBoardPageResponse;
import org.complete.challang.app.combination.service.CombinationBoardService;
import org.complete.challang.app.common.exception.SuccessResponse;
import org.complete.challang.app.common.service.SearchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Combination", description = "주류 조합 관련 API")
@RequestMapping("/combinations")
@RequiredArgsConstructor
@RestController
public class CombinationBoardController {

    private final CombinationBoardService combinationBoardService;
    private final SearchService searchService;

    @Operation(summary = "주류 조합 상세 조회", description = "주류 조합 단건 상세 조회")
    @GetMapping("/{combination_board_id}")
    public ResponseEntity<CombinationBoardFindResponse> findCombinationBoard(@PathVariable("combination_board_id") final Long combinationBoardId,
                                                                             @AuthUser final CustomOAuth2User customOAuth2User) {
        Long userId = customOAuth2User.getUserId();

        return new ResponseEntity<>(combinationBoardService.findCombinationBoard(combinationBoardId, userId), HttpStatus.OK);
    }

    @Operation(summary = "주류 조합 리스트 조회", description = "주류 조합 목록 조회")
    @GetMapping("/search")
    public ResponseEntity<CombinationBoardPageResponse<CombinationBoardListFindResponse>> findCombinationBoards(@RequestParam(value = "page", defaultValue = "1") final int page,
                                                                                                                @RequestParam("sorted") final String sorted,
                                                                                                                @RequestParam(value = "keyword", required = false) final String keyword,
                                                                                                                @RequestParam(value = "drink_id", required = false) final Long drinkId,
                                                                                                                @AuthUser final CustomOAuth2User customOAuth2User) {
        Long userId = customOAuth2User.getUserId();
        if (keyword != null) {
            return new ResponseEntity<>(searchService.findCombinationBoardsByKeyword(keyword, userId, sorted, page), HttpStatus.OK);
        }

        if (drinkId != null) {
            return new ResponseEntity<>(searchService.findCombinationBoardsByDrinkId(drinkId, userId, sorted, page), HttpStatus.OK);
        }


        return new ResponseEntity<>(combinationBoardService.findCombinationBoards(page, sorted, userId), HttpStatus.OK);
    }

    @Operation(summary = "주류 조합 작성", description = "주류 조합 게시판 작성")
    @PostMapping("")
    public ResponseEntity<CombinationBoardCreateUpdateResponse> createCombinationBoard(@RequestBody final CombinationBoardCreateRequest combinationBoardCreateRequest,
                                                                                       @AuthUser final CustomOAuth2User customOAuth2User) {
        Long userId = customOAuth2User.getUserId();

        return new ResponseEntity<>(combinationBoardService.createCombinationBoard(combinationBoardCreateRequest, userId), HttpStatus.CREATED);
    }

    @Operation(summary = "주류 조합 게시글 수정", description = "주류 조합 게시글 수정")
    @PatchMapping("/{combination_board_id}")
    public ResponseEntity<CombinationBoardCreateUpdateResponse> updateCombinationBoard(@PathVariable("combination_board_id") final Long combinationBoardId,
                                                                                       @RequestBody final CombinationBoardUpdateRequest combinationBoardUpdateRequest,
                                                                                       @AuthUser final CustomOAuth2User customOAuth2User) {
        Long userId = customOAuth2User.getUserId();

        return new ResponseEntity<>(combinationBoardService.updateCombinationBoard(combinationBoardId, combinationBoardUpdateRequest, userId), HttpStatus.CREATED);
    }

    @Operation(summary = "주류 조합 게시글 삭제", description = "주류 조합 게시글 삭제")
    @DeleteMapping("/{combination_board_id}")
    public ResponseEntity<SuccessResponse> deleteCombinationBoard(@PathVariable("combination_board_id") final Long combinationBoardId,
                                                                  @AuthUser final CustomOAuth2User customOAuth2User) {
        Long userId = customOAuth2User.getUserId();

        return new ResponseEntity<>(combinationBoardService.deleteCombinationBoard(combinationBoardId, userId), HttpStatus.OK);
    }

    @Operation(summary = "주류 조합 좋아요", description = "유저 주류 조합 좋아요 추가")
    @PostMapping("/{combination_board_id}/like")
    public ResponseEntity<SuccessResponse> createLikeCombinationBoard(@PathVariable("combination_board_id")
                                                                      @IdValid final Long combinationBoardId,
                                                                      @AuthUser final CustomOAuth2User customOAuth2User) {

        return new ResponseEntity<>(combinationBoardService.likeCombinationBoard(combinationBoardId, customOAuth2User), HttpStatus.CREATED);
    }

    @Operation(summary = "주류 조합 좋아요 삭제", description = "유저 주류 조합 좋아요 삭제")
    @DeleteMapping("/{combination_board_id}/like")
    public ResponseEntity<SuccessResponse> deleteLikeCombinationBoard(@PathVariable("combination_board_id")
                                                                      @IdValid final Long combinationBoardId,
                                                                      @AuthUser final CustomOAuth2User customOAuth2User) {

        return new ResponseEntity<>(combinationBoardService.unLikeCombinationBoard(combinationBoardId, customOAuth2User), HttpStatus.OK);
    }

    @Operation(summary = "주류 조합 북마크", description = "유저 주류 조합 북마크 추가")
    @PostMapping("/{combination_board_id}/bookmark")
    public ResponseEntity<SuccessResponse> createCombinationBoardBookmark(@PathVariable("combination_board_id")
                                                                          @IdValid final Long combinationBoardId,
                                                                          @AuthUser final CustomOAuth2User customOAuth2User) {

        return new ResponseEntity<>(combinationBoardService.createBookmark(combinationBoardId, customOAuth2User), HttpStatus.CREATED);
    }

    @Operation(summary = "주류 조합 북마크 삭제", description = "유저 주류 조합 북마크 삭제")
    @DeleteMapping("/{combination_board_id}/bookmark")
    public ResponseEntity<SuccessResponse> deleteCombinationBoardBookmark(@PathVariable("combination_board_id")
                                                                          @IdValid final Long combinationBoardId,
                                                                          @AuthUser final CustomOAuth2User customOAuth2User) {

        return new ResponseEntity<>(combinationBoardService.deleteBookmark(combinationBoardId, customOAuth2User), HttpStatus.OK);
    }
}
