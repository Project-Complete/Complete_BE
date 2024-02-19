package org.complete.challang.account.user.service;

import lombok.RequiredArgsConstructor;
import org.complete.challang.account.user.controller.dto.response.UserProfileFindResponse;
import org.complete.challang.account.user.domain.entity.User;
import org.complete.challang.account.user.domain.repository.UserRepository;
import org.complete.challang.common.exception.ApiException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.complete.challang.common.exception.ErrorCode.USER_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
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
}
