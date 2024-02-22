package org.complete.challang.account.user.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.complete.challang.account.user.controller.dto.request.ProfileUpdateRequest;
import org.complete.challang.account.user.controller.dto.response.ProfileUpdateResponse;
import org.complete.challang.account.user.controller.dto.response.UserProfileFindResponse;
import org.complete.challang.account.user.service.UserService;
import org.complete.challang.common.exception.SuccessCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/{user_id}")
    public ResponseEntity<UserProfileFindResponse> findUserProfile(@AuthenticationPrincipal final UserDetails user,
                                                                   @PathVariable(name = "user_id", required = false) final Long userId) {
        final Long myId = Long.parseLong(user.getUsername());
        final UserProfileFindResponse userProfileFindResponse = userService.findUserProfile(myId, userId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(userProfileFindResponse);
    }

    @PatchMapping()
    public ResponseEntity<ProfileUpdateResponse> updateProfile(@AuthenticationPrincipal final UserDetails user,
                                                               @RequestBody @Valid ProfileUpdateRequest profileUpdateRequest) {
        final Long userId = Long.parseLong(user.getUsername());
        final ProfileUpdateResponse profileUpdateResponse = userService.updateProfile(userId, profileUpdateRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(profileUpdateResponse);
    }

    @PostMapping("/{user_id}")
    public ResponseEntity<SuccessCode> createFollow(@AuthenticationPrincipal final UserDetails user,
                                                    @PathVariable("user_id") final Long followId) {
        final Long userId = Long.parseLong(user.getUsername());
        final SuccessCode successCode = userService.createFollow(userId, followId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(successCode);
    }
}
