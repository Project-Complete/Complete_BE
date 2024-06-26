package org.complete.challang.app.combination.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.complete.challang.annotation.AuthUser;
import org.complete.challang.app.account.oauth2.CustomOAuth2User;
import org.complete.challang.app.combination.controller.dto.request.CombinationBoardCreateRequest;
import org.complete.challang.app.combination.controller.dto.request.CombinationBoardUpdateRequest;
import org.complete.challang.app.combination.controller.dto.response.CombinationBoardCreateUpdateResponse;
import org.complete.challang.app.combination.controller.dto.response.CombinationBoardFindResponse;
import org.complete.challang.app.combination.controller.dto.response.CombinationBoardListFindResponse;
import org.complete.challang.app.combination.controller.dto.response.CombinationBoardPageResponse;
import org.complete.challang.app.combination.service.CombinationService;
import org.complete.challang.app.common.exception.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Combination", description = "주류 조합 관련 API")
@RequestMapping("/combinations")
@RequiredArgsConstructor
@RestController
public class CombinationController {

    private final CombinationService combinationService;
    private final ObjectMapper objectMapper;

    @Operation(summary = "주류 조합 상세 조회", description = "주류 조합 단건 상세 조회")
    @GetMapping("/{combination_board_id}")
    public ResponseEntity<CombinationBoardFindResponse> findCombinationBoard(@PathVariable("combination_board_id") final Long combinationBoardId,
                                                                             @AuthUser final CustomOAuth2User customOAuth2User) {
        Long userId = customOAuth2User.getUserId();

        return new ResponseEntity<>(combinationService.findCombinationBoard(combinationBoardId, userId), HttpStatus.OK);
    }

    @Operation(summary = "주류 조합 리스트 조회", description = "주류 조합 목록 조회")
    @GetMapping("/search")
    public ResponseEntity<CombinationBoardPageResponse<CombinationBoardListFindResponse>> findCombinationBoards(@RequestParam(value = "page", defaultValue = "1") final int page,
                                                                                                                @RequestParam("sorted") final String sorted,
                                                                                                                @AuthUser final CustomOAuth2User customOAuth2User) {
        Long userId = customOAuth2User.getUserId();

        return new ResponseEntity<>(combinationService.findCombinationBoards(page, sorted, userId), HttpStatus.OK);
    }

    @Operation(summary = "주류 조합 작성", description = "주류 조합 게시판 작성")
    @PostMapping("")
    public ResponseEntity<CombinationBoardCreateUpdateResponse> createCombinationBoard(@RequestBody final CombinationBoardCreateRequest combinationBoardCreateRequest,
                                                                                       @AuthUser final CustomOAuth2User customOAuth2User) {
        Long userId = customOAuth2User.getUserId();

        return new ResponseEntity<>(combinationService.createCombinationBoard(combinationBoardCreateRequest, userId), HttpStatus.CREATED);
    }

    @Operation(summary = "주류 조합 게시글 수정", description = "주류 조합 게시글 수정")
    @PatchMapping("/{combination_board_id}")
    public ResponseEntity<CombinationBoardCreateUpdateResponse> updateCombinationBoard(@PathVariable("combination_board_id") final Long combinationBoardId,
                                                                                       @RequestBody final CombinationBoardUpdateRequest combinationBoardUpdateRequest,
                                                                                       @AuthUser final CustomOAuth2User customOAuth2User) {
        Long userId = customOAuth2User.getUserId();

        return new ResponseEntity<>(combinationService.updateCombinationBoard(combinationBoardId, combinationBoardUpdateRequest, userId), HttpStatus.CREATED);
    }

    @Operation(summary = "주류 조합 게시글 삭제", description = "주류 조합 게시글 삭제")
    @DeleteMapping("/{combination_board_id}")
    public ResponseEntity<SuccessResponse> deleteCombinationBoard(@PathVariable("combination_board_id") final Long combinationBoardId,
                                                                  @AuthUser final CustomOAuth2User customOAuth2User) {
        Long userId = customOAuth2User.getUserId();

        return new ResponseEntity<>(combinationService.deleteCombinationBoard(combinationBoardId, userId), HttpStatus.OK);
    }
}
