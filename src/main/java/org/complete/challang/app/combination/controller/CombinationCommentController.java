package org.complete.challang.app.combination.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.complete.challang.annotation.AuthUser;
import org.complete.challang.app.account.oauth2.CustomOAuth2User;
import org.complete.challang.app.combination.controller.dto.request.CombinationCommentCreateRequest;
import org.complete.challang.app.combination.controller.dto.request.CombinationCommentUpdateRequest;
import org.complete.challang.app.combination.controller.dto.response.CombinationCommentPageResponse;
import org.complete.challang.app.combination.controller.dto.response.CombinationCommentResponse;
import org.complete.challang.app.combination.service.CombinationCommentService;
import org.complete.challang.app.common.exception.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Combination", description = "주류 조합 댓글 관련 API")
@RequestMapping("/combinations")
@RequiredArgsConstructor
@RestController
public class CombinationCommentController {

    private final CombinationCommentService combinationCommentService;

    @Operation(
            summary = "주류 조합 댓글 작성", description = "주류 조합 게시글 내 댓글 작성",
            parameters = {
                    @Parameter(name = "combination_board_id", description = "최상위 댓글은 'null'을 입력")
            }
    )
    @PostMapping("/{combination_board_id}/comment")
    public ResponseEntity<CombinationCommentResponse> createComment(@PathVariable("combination_board_id") final Long combinationBoardId,
                                                                    @RequestBody final CombinationCommentCreateRequest combinationCommentCreateRequest,
                                                                    @AuthUser CustomOAuth2User customOAuth2User) {
        Long userId = customOAuth2User.getUserId();

        return new ResponseEntity<>(
                combinationCommentService.createComment(
                        combinationBoardId,
                        combinationCommentCreateRequest,
                        userId
                ),
                HttpStatus.CREATED
        );
    }

    @Operation(summary = "주류 조합 댓글 리스트 조회", description = "주류 조합 게시글 내 댓글 리스트 조회")
    @GetMapping("/{combination_board_id}/comment")
    public ResponseEntity<CombinationCommentPageResponse<CombinationCommentResponse>> findComments(@PathVariable("combination_board_id") final Long combinationBoardId,
                                                                                                   @RequestParam(value = "page", defaultValue = "1") int page) {

        return new ResponseEntity<>(combinationCommentService.findComments(combinationBoardId, page), HttpStatus.OK);
    }

    @Operation(summary = "주류 조합 답글 리스트 조회", description = "주류 조합 게시글 내 답글 리스트 조회")
    @GetMapping("/comment/{combination_comment_id}")
    public ResponseEntity<CombinationCommentPageResponse<CombinationCommentResponse>> findReplyComments(@PathVariable("combination_comment_id") final Long combinationCommentId,
                                                                                                        @RequestParam(value = "page", defaultValue = "1") int page) {

        return new ResponseEntity<>(combinationCommentService.findReplyComments(combinationCommentId, page), HttpStatus.OK);
    }

    @Operation(summary = "주류 조합 댓글 수정", description = "주류 조합 게시글 내 댓글 수정")
    @PatchMapping("/comment/{combination_comment_id}")
    public ResponseEntity<CombinationCommentResponse> updateComment(@PathVariable("combination_comment_id") final Long combinationCommentId,
                                                                    @RequestBody final CombinationCommentUpdateRequest combinationCommentUpdateRequest,
                                                                    @AuthUser final CustomOAuth2User customOAuth2User) {
        Long userId = customOAuth2User.getUserId();

        return new ResponseEntity<>(combinationCommentService.updateComment(combinationCommentId, combinationCommentUpdateRequest, userId), HttpStatus.CREATED);
    }

    @Operation(summary = "주류 조합 댓글 삭제", description = "주류 조합 게시글 내 댓글 삭제")
    @DeleteMapping("/comment/{combination_comment_id}")
    public ResponseEntity<SuccessResponse> deleteComment(@PathVariable("combination_comment_id") final Long combinationCommentId,
                                                         @AuthUser final CustomOAuth2User customOAuth2User) {
        Long userId = customOAuth2User.getUserId();

        return new ResponseEntity<>(combinationCommentService.deleteComment(combinationCommentId, userId), HttpStatus.OK);
    }
}
