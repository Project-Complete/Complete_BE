package org.complete.challang.common.controller;

import lombok.RequiredArgsConstructor;
import org.complete.challang.common.controller.dto.request.PreSignedUrlGetRequest;
import org.complete.challang.common.controller.dto.response.PreSignedUrlGetResponse;
import org.complete.challang.common.service.S3Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;

    @GetMapping("/pre-signed-url")
    public ResponseEntity<PreSignedUrlGetResponse> getPreSignedUrl(@RequestBody PreSignedUrlGetRequest preSignedUrlGetRequest,
                                                                   @AuthenticationPrincipal UserDetails userDetails) {
        return new ResponseEntity<>(s3Service.getPreSignedUrl(preSignedUrlGetRequest, userDetails), HttpStatus.OK);
    }
}
