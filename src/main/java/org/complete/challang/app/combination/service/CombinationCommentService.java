package org.complete.challang.app.combination.service;

import lombok.RequiredArgsConstructor;
import org.complete.challang.app.account.user.domain.entity.User;
import org.complete.challang.app.account.user.domain.repository.UserRepository;
import org.complete.challang.app.combination.controller.dto.request.CombinationCommentCreateRequest;
import org.complete.challang.app.combination.controller.dto.request.CombinationCommentUpdateRequest;
import org.complete.challang.app.combination.controller.dto.response.CombinationCommentPageResponse;
import org.complete.challang.app.combination.controller.dto.response.CombinationCommentResponse;
import org.complete.challang.app.combination.domain.entity.CombinationBoard;
import org.complete.challang.app.combination.domain.entity.CombinationComment;
import org.complete.challang.app.combination.domain.entity.CombinationSortCriteria;
import org.complete.challang.app.combination.domain.repository.CombinationBoardRepository;
import org.complete.challang.app.combination.domain.repository.CombinationCommentRepository;
import org.complete.challang.app.common.exception.ApiException;
import org.complete.challang.app.common.exception.ErrorCode;
import org.complete.challang.app.common.exception.SuccessCode;
import org.complete.challang.app.common.exception.SuccessResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CombinationCommentService {

    private final CombinationCommentRepository combinationCommentRepository;
    private final CombinationBoardRepository combinationBoardRepository;
    private final UserRepository userRepository;

    @Transactional
    public CombinationCommentResponse createComment(final Long combinationBoardId,
                                                    final CombinationCommentCreateRequest combinationCommentCreateRequest,
                                                    final Long userId) {
        User user = userRepository.getReferenceById(userId);
        CombinationBoard combinationBoard = combinationBoardRepository.findById(combinationBoardId).orElseThrow(() -> new ApiException(ErrorCode.COMBINATION_BOARD_NOT_FOUND));
        CombinationComment combinationComment = combinationCommentCreateRequest.toEntity(combinationBoard, user);

        if (combinationCommentCreateRequest.getParentCombinationCommentId() != null) {
            CombinationComment parentComment = findCombinationCommentById(combinationCommentCreateRequest.getParentCombinationCommentId());
            if (parentComment.getDepth() > 0) {
                throw new ApiException(ErrorCode.REPLY_COMMENT_DEPTH_EXCEPTION);
            }
            combinationComment.addReplyComment(parentComment);
        }
        combinationCommentRepository.save(combinationComment);

        return CombinationCommentResponse.toDto(combinationComment);
    }

    public CombinationCommentPageResponse<CombinationCommentResponse> findComments(final Long combinationBoardId,
                                                                                   final int page) {
        Page<CombinationComment> combinationComments = combinationCommentRepository.findCommentByCombinationBoardId(
                combinationBoardId,
                PageRequest.of(page - 1, 8)
        );

        List<CombinationCommentResponse> combinationCommentResponses = combinationComments.stream()
                .map(CombinationCommentResponse::toDto)
                .toList();

        return CombinationCommentPageResponse.toDto(combinationCommentResponses, combinationComments, CombinationSortCriteria.COMBINATION_LATEST_DESC.getDescription());
    }

    public CombinationCommentPageResponse<CombinationCommentResponse> findReplyComments(final Long combinationCommentId,
                                                                                        final int page) {
        Page<CombinationComment> replyComments = combinationCommentRepository.findReplyCommentByCombinationCommentId(combinationCommentId, PageRequest.of(page - 1, 8));
        List<CombinationCommentResponse> replyCommentResponses = replyComments.stream()
                .map(CombinationCommentResponse::toDto)
                .toList();

        return CombinationCommentPageResponse.toDto(replyCommentResponses, replyComments, CombinationSortCriteria.COMBINATION_LATEST_DESC.getDescription());
    }

    @Transactional
    public CombinationCommentResponse updateComment(final Long combinationCommentId,
                                                    final CombinationCommentUpdateRequest combinationCommentUpdateRequest,
                                                    final Long userId) {
        CombinationComment combinationComment = findCombinationCommentById(combinationCommentId);
        authorizeUser(userId, combinationComment);

        combinationComment.updateComment(combinationCommentUpdateRequest);

        return CombinationCommentResponse.toDto(combinationComment);
    }

    @Transactional
    public SuccessResponse deleteComment(final Long combinationCommentId,
                                         final Long userId) {
        CombinationComment combinationComment = findCombinationCommentById(combinationCommentId);
        authorizeUser(userId, combinationComment);

        if (combinationComment.getParent() != null) {
            combinationComment.deleteComment();
            return SuccessResponse.toSuccessResponse(SuccessCode.COMBINATION_REPLY_COMMENT_DELETE_SUCCESS);
        }

        combinationCommentRepository.delete(combinationComment);
        return SuccessResponse.toSuccessResponse(SuccessCode.COMBINATION_COMMENT_DELETE_SUCCESS);
    }

    private void authorizeUser(Long userId, CombinationComment combinationComment) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

        if (!combinationComment.getUser().getId().equals(user.getId())) {
            throw new ApiException(ErrorCode.COMBINATION_COMMENT_USER_FORBIDDEN);
        }
    }

    private CombinationComment findCombinationCommentById(Long combinationCommentId) {
        return combinationCommentRepository.findById(combinationCommentId).orElseThrow(() -> new ApiException(ErrorCode.COMBINATION_COMMENT_NOT_FOUND));
    }
}
