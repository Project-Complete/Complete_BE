package org.complete.challang.account.user.service;

import lombok.RequiredArgsConstructor;
import org.complete.challang.account.user.controller.dto.request.ProfileUpdateRequest;
import org.complete.challang.account.user.controller.dto.response.ProfileUpdateResponse;
import org.complete.challang.account.user.controller.dto.response.UserProfileFindResponse;
import org.complete.challang.account.user.domain.entity.User;
import org.complete.challang.account.user.domain.repository.UserRepository;
import org.complete.challang.common.exception.ApiException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.complete.challang.common.exception.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserProfileFindResponse findUserProfile(final Long myId,
                                                   final Long userId) {
        User user;
        if (userId == null) {
            user = userRepository.findById(myId)
                    .orElseThrow(() -> new ApiException(USER_NOT_FOUND));

            return UserProfileFindResponse.toDto(user, user.getEmail());
        } else {
            user = userRepository.findById(userId)
                    .orElseThrow(() -> new ApiException(USER_NOT_FOUND));

            return UserProfileFindResponse.toDto(user, null);
        }
    }

    @Transactional
    public ProfileUpdateResponse updateProfile(final Long userId,
                                               final ProfileUpdateRequest profileUpdateRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(USER_NOT_FOUND));

        user.updateProfileImageUrl(profileUpdateRequest.getProfileImageUrl());
        updateEmail(user, profileUpdateRequest.getEmail());
        updateNickname(user, profileUpdateRequest.getNickname());

        return ProfileUpdateResponse.toDto(user);
    }

    private void updateEmail(final User user,
                             final String newEmail) {
        if (user.getEmail() == newEmail) {
            return;
        }
        if (userRepository.existsByEmail(newEmail)) {
            new ApiException(EMAIL_CONFLICT);
        }
        user.updateEmail(newEmail);
    }

    private void updateNickname(final User user,
                                final String newNickname) {
        if (user.getNickname() == newNickname) {
            return;
        }
        if (userRepository.existsByNickname(newNickname)) {
            new ApiException(NICKNAME_CONFLICT);
        }
        user.updateNickname(newNickname);
    }
}
