package org.complete.challang.app.account.user.service;

import lombok.RequiredArgsConstructor;
import org.complete.challang.app.account.user.controller.dto.item.FollowDto;
import org.complete.challang.app.account.user.controller.dto.request.ProfileUpdateRequest;
import org.complete.challang.app.account.user.controller.dto.response.FollowCheckResponse;
import org.complete.challang.app.account.user.controller.dto.response.FollowsFindResponse;
import org.complete.challang.app.account.user.controller.dto.response.ProfileUpdateResponse;
import org.complete.challang.app.account.user.controller.dto.response.UserProfileFindResponse;
import org.complete.challang.app.account.user.domain.entity.Follow;
import org.complete.challang.app.account.user.domain.entity.User;
import org.complete.challang.app.account.user.domain.repository.FollowRepository;
import org.complete.challang.app.account.user.domain.repository.UserRepository;
import org.complete.challang.app.combination.controller.dto.response.CombinationBoardListFindResponse;
import org.complete.challang.app.combination.controller.dto.response.CombinationBoardPageResponse;
import org.complete.challang.app.combination.domain.entity.CombinationSortCriteria;
import org.complete.challang.app.combination.domain.repository.CombinationBoardRepository;
import org.complete.challang.app.common.exception.ApiException;
import org.complete.challang.app.common.exception.SuccessCode;
import org.complete.challang.app.drink.controller.dto.response.DrinkListFindResponse;
import org.complete.challang.app.drink.controller.dto.response.DrinkPageResponse;
import org.complete.challang.app.drink.domain.entity.criteria.DrinkSortCriteria;
import org.complete.challang.app.drink.domain.repository.DrinkRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.complete.challang.app.common.exception.ErrorCode.*;
import static org.complete.challang.app.common.exception.SuccessCode.FOLLOW_CREATE_SUCCESS;
import static org.complete.challang.app.common.exception.SuccessCode.FOLLOW_DELETE_SUCCESS;

@RequiredArgsConstructor
@Service
public class UserService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final DrinkRepository drinkRepository;
    private final CombinationBoardRepository combinationBoardRepository;

    public UserProfileFindResponse findUserProfile(final Long requestUserId,
                                                   final Long targetUserId) {
        User user;
        if (targetUserId == null) {
            user = findUserById(requestUserId);

            return UserProfileFindResponse.toDto(user, user.getEmail());
        } else {
            user = findUserById(targetUserId);

            return UserProfileFindResponse.toDto(user, null);
        }
    }

    @Transactional
    public ProfileUpdateResponse updateProfile(final Long userId,
                                               final ProfileUpdateRequest profileUpdateRequest) {
        final User user = findUserById(userId);

        user.updateProfileImageUrl(profileUpdateRequest.getProfileImageUrl());
        updateEmail(user, profileUpdateRequest.getEmail());
        updateNickname(user, profileUpdateRequest.getNickname());

        return ProfileUpdateResponse.toDto(user);
    }

    @Transactional
    public SuccessCode createFollow(final Long requestUserId,
                                    final Long targetUserId) {
        final User fromUser = findUserById(requestUserId);
        final User toUSer = findUserById(targetUserId);

        if (fromUser == toUSer) {
            throw new ApiException(INVALID_FOLLOW_REQUEST);
        }
        if (followRepository.existsByFromUserAndToUser(fromUser, toUSer)) {
            throw new ApiException(FOLLOW_CONFLICT);
        }

        final Follow follow = Follow.builder()
                .fromUser(fromUser)
                .toUser(toUSer)
                .build();
        followRepository.save(follow);

        return FOLLOW_CREATE_SUCCESS;
    }

    @Transactional(readOnly = true)
    public FollowsFindResponse findFollows(final Long requestUserId,
                                           final Long targetUserId) {
        final User requestUser = findUserById(requestUserId);
        final User targetUser = findUserById(targetUserId);

        final List<FollowDto> followers = findFollowers(requestUser, targetUser);
        final List<FollowDto> followings = findFollowings(requestUser, targetUser);

        return FollowsFindResponse.toDto(followers, followings);
    }

    @Transactional
    public SuccessCode deleteFollow(final Long requestUserId,
                                    final Long targetUserId) {
        final User requestUser = findUserById(requestUserId);
        final User targetUser = findUserById(targetUserId);

        if (!checkFollow(requestUserId, targetUserId)) {
            throw new ApiException(FOLLOW_NOT_FOUND);
        }
        followRepository.deleteByFromUserAndToUser(requestUser, targetUser);

        return FOLLOW_DELETE_SUCCESS;
    }

    public FollowCheckResponse checkFollowing(final Long requestUserId,
                                              final Long targetUserId) {

        return FollowCheckResponse.toDto(checkFollow(requestUserId, targetUserId));
    }

    @Transactional(readOnly = true)
    public DrinkPageResponse<DrinkListFindResponse> findLikeDrinks(final Long userId,
                                                                   final int page,
                                                                   final int size) {
        final User user = findUserById(userId);
        final Page<DrinkListFindResponse> drinks = drinkRepository.findByUserLike(user.getId(), PageRequest.of(page - 1, size));

        final DrinkSortCriteria drinkSortCriteria = DrinkSortCriteria.getDrinkSortCriteria("latest");

        return DrinkPageResponse.toDto(drinks.getContent(), drinks, drinkSortCriteria.getDescription());
    }

    @Transactional(readOnly = true)
    public CombinationBoardPageResponse<CombinationBoardListFindResponse> findCombinationBoards(final Long userId,
                                                                                                final int page,
                                                                                                final int size) {
        final User user = findUserById(userId);
        final CombinationSortCriteria combinationSortCriteria = CombinationSortCriteria.getCombinationSortCriteria("latest");

        final Page<CombinationBoardListFindResponse> combinationBoardListFindResponses = combinationBoardRepository.findAllByUser(
                user.getId(),
                combinationSortCriteria,
                PageRequest.of(page - 1, size)
        );

        return CombinationBoardPageResponse.toDto(
                combinationBoardListFindResponses.getContent(),
                combinationBoardListFindResponses,
                combinationSortCriteria.getDescription()
        );
    }

    @Transactional(readOnly = true)
    public CombinationBoardPageResponse<CombinationBoardListFindResponse> findLikeCombinationBoards(final Long userId,
                                                                                                    final int page,
                                                                                                    final int size) {
        final User user = findUserById(userId);
        final CombinationSortCriteria combinationSortCriteria = CombinationSortCriteria.getCombinationSortCriteria("latest");

        final Page<CombinationBoardListFindResponse> combinationBoardListFindResponses = combinationBoardRepository.findAllByUserLike(
                user.getId(),
                combinationSortCriteria,
                PageRequest.of(page - 1, size)
        );

        return CombinationBoardPageResponse.toDto(
                combinationBoardListFindResponses.getContent(),
                combinationBoardListFindResponses,
                combinationSortCriteria.getDescription()
        );
    }

    @Transactional(readOnly = true)
    public CombinationBoardPageResponse<CombinationBoardListFindResponse> findBookmarkCombinationBoards(final Long userId,
                                                                                                        final int page,
                                                                                                        final int size) {
        final User user = findUserById(userId);
        final CombinationSortCriteria combinationSortCriteria = CombinationSortCriteria.getCombinationSortCriteria("latest");

        final Page<CombinationBoardListFindResponse> combinationBoardListFindResponses = combinationBoardRepository.findAllByUserBookmark(
                user.getId(),
                combinationSortCriteria,
                PageRequest.of(page - 1, size)
        );

        return CombinationBoardPageResponse.toDto(
                combinationBoardListFindResponses.getContent(),
                combinationBoardListFindResponses,
                combinationSortCriteria.getDescription()
        );
    }

    private User findUserById(final Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(USER_NOT_FOUND));
    }

    private void updateEmail(final User user,
                             final String newEmail) {
        if (user.getEmail().equals(newEmail)) {
            return;
        }
        if (userRepository.existsByEmail(newEmail)) {
            throw new ApiException(EMAIL_CONFLICT);
        }
        user.updateEmail(newEmail);
    }

    private void updateNickname(final User user,
                                final String newNickname) {
        if (user.getNickname().equals(newNickname)) {
            return;
        }
        if (userRepository.existsByNickname(newNickname)) {
            throw new ApiException(NICKNAME_CONFLICT);
        }
        user.updateNickname(newNickname);
    }

    private List<FollowDto> findFollowers(final User requestUser,
                                          final User targetUser) {
        return targetUser.getFollowers()
                .stream()
                .map(follow -> {
                            final User followUser = follow.getFromUser();
                            final boolean isFollow = checkFollow(requestUser.getId(), followUser.getId());

                            return FollowDto.toDto(followUser, isFollow);
                        }
                ).collect(Collectors.toList());
    }

    private List<FollowDto> findFollowings(final User requestUser,
                                           final User targetUser) {
        return targetUser.getFollowings()
                .stream()
                .map(follow -> {
                            final User followUser = follow.getToUser();
                            final boolean isFollow = checkFollow(requestUser.getId(), followUser.getId());

                            return FollowDto.toDto(followUser, isFollow);
                        }
                ).collect(Collectors.toList());
    }

    private boolean checkFollow(final Long fromUserId,
                                final Long toUserId) {
        User fromUser = findUserById(fromUserId);
        User toUser = findUserById(toUserId);

        return followRepository.existsByFromUserAndToUser(fromUser, toUser);
    }
}
