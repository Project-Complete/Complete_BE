package org.complete.challang.account.user.controller;


import lombok.RequiredArgsConstructor;
import org.complete.challang.account.user.controller.dto.response.UserProfileFindResponse;
import org.complete.challang.account.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
