package org.complete.challang.app.combination.service;

import lombok.RequiredArgsConstructor;
import org.complete.challang.app.account.user.domain.entity.User;
import org.complete.challang.app.account.user.domain.repository.UserRepository;
import org.complete.challang.app.combination.controller.dto.item.CombinationCreateUpdateDto;
import org.complete.challang.app.combination.controller.dto.request.CombinationBoardCreateRequest;
import org.complete.challang.app.combination.controller.dto.request.CombinationBoardUpdateRequest;
import org.complete.challang.app.combination.controller.dto.response.CombinationBoardCreateUpdateResponse;
import org.complete.challang.app.combination.controller.dto.response.CombinationBoardFindResponse;
import org.complete.challang.app.combination.controller.dto.response.CombinationBoardListFindResponse;
import org.complete.challang.app.combination.controller.dto.response.CombinationBoardPageResponse;
import org.complete.challang.app.combination.domain.entity.Combination;
import org.complete.challang.app.combination.domain.entity.CombinationBoard;
import org.complete.challang.app.combination.domain.entity.CombinationSortCriteria;
import org.complete.challang.app.combination.domain.repository.CombinationBoardRepository;
import org.complete.challang.app.common.exception.ApiException;
import org.complete.challang.app.common.exception.ErrorCode;
import org.complete.challang.app.drink.domain.entity.Drink;
import org.complete.challang.app.drink.domain.repository.DrinkRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CombinationService {

    private final CombinationBoardRepository combinationBoardRepository;
    private final UserRepository userRepository;
    private final DrinkRepository drinkRepository;

    public CombinationBoardFindResponse findCombinationBoard(final Long combinationBoardId,
                                                             final Long userId) {
        final CombinationBoard combinationBoard = findByCombinationBoard(combinationBoardId);

        return CombinationBoardFindResponse.toDto(combinationBoard, userId);
    }

    public CombinationBoardPageResponse<CombinationBoardListFindResponse> findCombinationBoards(final int page,
                                                                                                final String sorted,
                                                                                                final Long userId) {
        CombinationSortCriteria combinationSortCriteria = CombinationSortCriteria.getCombinationSortCriteria(sorted);
        Page<CombinationBoardListFindResponse> combinations = combinationBoardRepository.findAllBySorted(
                combinationSortCriteria,
                PageRequest.of(page - 1, 8),
                userId
        );

        return CombinationBoardPageResponse.toDto(combinations.getContent(), combinations, combinationSortCriteria.getDescription());
    }

    @Transactional
    public CombinationBoardCreateUpdateResponse createCombinationBoard(final CombinationBoardCreateRequest combinationBoardCreateRequest,
                                                                       final Long userId) {
        final User user = findByUser(userId);
        CombinationBoard combinationBoard = combinationBoardCreateRequest.toEntity(user);
        Set<Combination> combinations = toCombinations(combinationBoardCreateRequest.getCombinations(), combinationBoard);
        combinationBoard.updateCombinations(combinations);
        final CombinationBoard savedCombinationBoard = combinationBoardRepository.save(combinationBoard);

        return CombinationBoardCreateUpdateResponse.toDto(savedCombinationBoard);
    }

    @Transactional
    public CombinationBoardCreateUpdateResponse updateCombinationBoard(final Long combinationBoardId,
                                                                       final CombinationBoardUpdateRequest combinationBoardUpdateRequest,
                                                                       final Long userId) {
        CombinationBoard combinationBoard = findByCombinationBoard(combinationBoardId);
        User user = findByUser(userId);

        if (combinationBoard.getUser().getId() != user.getId()) {
            throw new ApiException(ErrorCode.COMBINATION_UPDATE_FORBIDDEN);
        }

        combinationBoard.updateCombinationBoard(combinationBoardUpdateRequest);
        Set<Combination> combinations = toCombinations(combinationBoardUpdateRequest.getCombinations(), combinationBoard);
        combinationBoard.updateCombinations(combinations);

        return CombinationBoardCreateUpdateResponse.toDto(combinationBoard);
    }

    private Set<Combination> toCombinations(List<CombinationCreateUpdateDto> combinationBoardUpdateRequest, CombinationBoard combinationBoard) {
        return combinationBoardUpdateRequest
                .stream()
                .map(combinationCreateUpdateDto -> {
                    Drink drink = combinationCreateUpdateDto.getDrinkId() != null
                            ? drinkRepository.findById(combinationCreateUpdateDto.getDrinkId()).orElseThrow(() -> new ApiException(ErrorCode.DRINK_NOT_FOUND))
                            : null;
                    return combinationCreateUpdateDto.toEntity(combinationBoard, drink);
                })
                .collect(Collectors.toSet());
    }

    private User findByUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
    }

    private CombinationBoard findByCombinationBoard(Long combinationBoardId) {
        return combinationBoardRepository.findById(combinationBoardId).orElseThrow(() -> new ApiException(ErrorCode.COMBINATION_BOARD_NOT_FOUND));
    }
}
