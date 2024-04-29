package org.complete.challang.app.common.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.complete.challang.annotation.AuthUser;
import org.complete.challang.app.account.oauth2.CustomOAuth2User;
import org.complete.challang.app.common.controller.dto.request.PreSignedUrlFindRequest;
import org.complete.challang.app.common.controller.dto.response.PreSignedUrlFindResponse;
import org.complete.challang.app.common.service.S3Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Pre-Signed URL", description = "S3 pre-signed url 요청 API")
@RequiredArgsConstructor
@RestController
public class S3Controller {

    private final S3Service s3Service;

    @Operation(summary = "Pre-Signed URL 요청", description = "pre-signed url 반환 요청")
    @PostMapping("/pre-signed-url")
    public ResponseEntity<PreSignedUrlFindResponse> findPreSignedUrl(@RequestBody final PreSignedUrlFindRequest preSignedUrlFindRequest,
                                                                     @AuthUser final CustomOAuth2User customOAuth2User) {

        return new ResponseEntity<>(s3Service.findPreSignedUrl(preSignedUrlFindRequest, customOAuth2User), HttpStatus.OK);
    }
}
