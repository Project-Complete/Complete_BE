package org.complete.challang.app.account.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.complete.challang.annotation.AuthUser;
import org.complete.challang.app.account.oauth2.CustomOAuth2User;
import org.complete.challang.app.account.user.controller.dto.request.ProfileUpdateRequest;
import org.complete.challang.app.account.user.controller.dto.response.FollowsFindResponse;
import org.complete.challang.app.account.user.controller.dto.response.ProfileUpdateResponse;
import org.complete.challang.app.account.user.controller.dto.response.UserProfileFindResponse;
import org.complete.challang.app.account.user.service.UserService;
import org.complete.challang.app.common.exception.SuccessCode;
import org.complete.challang.app.drink.controller.dto.response.DrinkListFindResponse;
import org.complete.challang.app.drink.controller.dto.response.DrinkPageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Users",
        description = "사용자 관련 API - 마이페이지, 팔로우, 북마크&좋아요 게시물 조회")
@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;

    @Operation(summary = "사용자 프로필 조회",
            description = "특정 사용자의 프로필 정보 반환, 자신의 정보 조회 시에만 email 정보 반환")
    @GetMapping({"", "/{user_id}"})
    public ResponseEntity<UserProfileFindResponse> findUserProfile(@AuthenticationPrincipal final UserDetails user,
                                                                   @PathVariable(name = "user_id", required = false) final Long targetUserId) {
        final Long requestUserId = Long.parseLong(user.getUsername());
        final UserProfileFindResponse userProfileFindResponse = userService.findUserProfile(requestUserId, targetUserId);

        return new ResponseEntity<>(userProfileFindResponse, HttpStatus.OK);
    }

    @Operation(summary = "사용자 프로필 조회",
            description = "특정 사용자의 프로필 정보 반환, 자신의 정보 조회 시에만 email 정보 반환")
    @GetMapping("/{user_id}")
    public ResponseEntity<UserProfileFindResponse> findMyProfile(@AuthenticationPrincipal final UserDetails user,
                                                                 @PathVariable(name = "user_id", required = false) final Long targetUserId) {
        final Long requestUserId = Long.parseLong(user.getUsername());
        final UserProfileFindResponse userProfileFindResponse = userService.findUserProfile(requestUserId, targetUserId);

        return new ResponseEntity<>(userProfileFindResponse, HttpStatus.OK);
    }

    @Operation(summary = "사용자 프로필 업데이트",
            description = "정상적으로 업데이트 된 사용자의 프로필 반환, 닉네임(중복체크), 이메일(중복체크), 프로필 이미지 변경 가능")
    @PatchMapping()
    public ResponseEntity<ProfileUpdateResponse> updateProfile(@AuthenticationPrincipal final UserDetails user,
                                                               @RequestBody @Valid ProfileUpdateRequest profileUpdateRequest) {
        final Long userId = Long.parseLong(user.getUsername());
        final ProfileUpdateResponse profileUpdateResponse = userService.updateProfile(userId, profileUpdateRequest);

        return new ResponseEntity<>(profileUpdateResponse, HttpStatus.CREATED);
    }

    @Operation(summary = "사용자 팔로우",
            description = "성공 코드 반환, 팔로우 하지 않은 특정 사용자를 팔로우")
    @PostMapping("/{user_id}")
    public ResponseEntity<SuccessCode> createFollow(@AuthenticationPrincipal final UserDetails user,
                                                    @PathVariable("user_id") final Long targetUserId) {
        final Long requestUserId = Long.parseLong(user.getUsername());
        final SuccessCode successCode = userService.createFollow(requestUserId, targetUserId);

        return new ResponseEntity<>(successCode, HttpStatus.CREATED);
    }

    @Operation(summary = "사용자 팔로잉, 팔로워 리스트 조회",
            description = "팔로잉, 팔로워의 간단한 정보 및 목록을 조회한 사용자가 특정 사용자를 팔로우 했는지의 유무를 반환")
    @GetMapping("/follows/{user_id}")
    public ResponseEntity<FollowsFindResponse> findFollows(@AuthenticationPrincipal final UserDetails user,
                                                           @PathVariable("user_id") final Long targetUserId) {
        final Long requestUserId = Long.parseLong(user.getUsername());
        final FollowsFindResponse followsFindResponse = userService.findFollows(requestUserId, targetUserId);

        return new ResponseEntity<>(followsFindResponse, HttpStatus.OK);
    }

    @Operation(summary = "사용자 팔로우 취소",
            description = "성공 코드 반환, 팔로우 한 특정 사용자의 팔로우 취소")
    @DeleteMapping("/{user_id}")
    public ResponseEntity<SuccessCode> deleteFollow(@AuthenticationPrincipal final UserDetails user,
                                                    @PathVariable("user_id") final Long targetUserId) {
        final Long requestUserId = Long.parseLong(user.getUsername());
        final SuccessCode successCode = userService.deleteFollow(requestUserId, targetUserId);

        return new ResponseEntity<>(successCode, HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "사용자 좋아요 주류 조회",
            description = "성공 코드 반환, 좋아요 누른 주류 리스트 조회")
    @GetMapping("/drinks/like")
    public ResponseEntity<DrinkPageResponse<DrinkListFindResponse>> findLikeDrinks(@AuthUser final CustomOAuth2User customOAuth2User,
                                                                                   @RequestParam(value = "page", defaultValue = "1") final int page,
                                                                                   @RequestParam(value = "size", defaultValue = "3") final int size) {
        final Long userId = customOAuth2User.getUserId();
        final DrinkPageResponse<DrinkListFindResponse> drinks = userService.findLikeDrinks(userId, page, size);

        return new ResponseEntity<>(drinks, HttpStatus.OK);
    }
}
