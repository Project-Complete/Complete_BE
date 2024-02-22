package org.complete.challang.account.user.service;

import lombok.RequiredArgsConstructor;
import org.complete.challang.account.user.controller.dto.request.ProfileUpdateRequest;
import org.complete.challang.account.user.controller.dto.response.ProfileUpdateResponse;
import org.complete.challang.account.user.controller.dto.response.UserProfileFindResponse;
import org.complete.challang.account.user.domain.entity.Follow;
import org.complete.challang.account.user.domain.entity.User;
import org.complete.challang.account.user.domain.repository.FollowRepository;
import org.complete.challang.account.user.domain.repository.UserRepository;
import org.complete.challang.common.exception.ApiException;
import org.complete.challang.common.exception.SuccessCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.complete.challang.common.exception.ErrorCode.*;
import static org.complete.challang.common.exception.SuccessCode.FOLLOW_CREATE_SUCCESS;

@RequiredArgsConstructor
@Service
public class UserService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public UserProfileFindResponse findUserProfile(final Long myId,
                                                   final Long userId) {
        User user;
        if (userId == null) {
            user = findUserById(myId);

            return UserProfileFindResponse.toDto(user, user.getEmail());
        } else {
            user = findUserById(userId);

            return UserProfileFindResponse.toDto(user, null);
        }
    }

    @Transactional
    public ProfileUpdateResponse updateProfile(final Long userId,
                                               final ProfileUpdateRequest profileUpdateRequest) {
        User user = findUserById(userId);

        user.updateProfileImageUrl(profileUpdateRequest.getProfileImageUrl());
        updateEmail(user, profileUpdateRequest.getEmail());
        updateNickname(user, profileUpdateRequest.getNickname());

        return ProfileUpdateResponse.toDto(user);
    }

    @Transactional
    public SuccessCode createFollow(final Long userId,
                                    final Long followId) {
        User fromUser = findUserById(userId);
        User toUSer = findUserById(followId);
        if (fromUser == toUSer) {
            throw new ApiException(INVALID_FOLLOW_REQUEST);
        }
        if (followRepository.existsByFromUserIdAndToUserId(fromUser.getId(), toUSer.getId())) {
            throw new ApiException(FOLLOW_CONFLICT);
        }

        Follow follow = Follow.builder()
                .fromUser(fromUser)
                .toUser(toUSer)
                .build();
        followRepository.save(follow);

        return FOLLOW_CREATE_SUCCESS;
    }

    private User findUserById(final Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(USER_NOT_FOUND));
    }

    private void updateEmail(final User user,
                             final String newEmail) {
        if (user.getEmail() == newEmail) {
            return;
        }
        if (userRepository.existsByEmail(newEmail)) {
            throw new ApiException(EMAIL_CONFLICT);
        }
        user.updateEmail(newEmail);
    }

    private void updateNickname(final User user,
                                final String newNickname) {
        if (user.getNickname() == newNickname) {
            return;
        }
        if (userRepository.existsByNickname(newNickname)) {
            throw new ApiException(NICKNAME_CONFLICT);
        }
        user.updateNickname(newNickname);
    }
}
