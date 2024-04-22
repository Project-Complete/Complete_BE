package org.complete.challang.app.common.service;

import lombok.RequiredArgsConstructor;
import org.complete.challang.app.account.user.domain.repository.UserRepository;
import org.complete.challang.app.common.exception.ApiException;
import org.complete.challang.app.common.exception.ErrorCode;
import org.complete.challang.app.common.controller.dto.request.PreSignedUrlFindRequest;
import org.complete.challang.app.common.controller.dto.response.PreSignedUrlFindResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.net.URL;
import java.time.Duration;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class S3Service {

    @Value("${cloud.s3.bucket}")
    private String bucket;

    private final S3Presigner s3Presigner;
    private final UserRepository userRepository;

    public PreSignedUrlFindResponse findPreSignedUrl(final PreSignedUrlFindRequest presignedUrlFindRequest,
                                                     final UserDetails userDetails) {
        if (!userRepository.existsById(Long.valueOf(userDetails.getUsername()))) {
            throw new ApiException(ErrorCode.USER_NOT_FOUND);
        }

        final PutObjectRequest putObjectRequest = generatePutObjectRequest(presignedUrlFindRequest, userDetails.getUsername());
        final PutObjectPresignRequest putObjectPresignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofSeconds(30))
                .putObjectRequest(putObjectRequest)
                .build();
        final PresignedPutObjectRequest presignedPutObjectRequest = s3Presigner.presignPutObject(putObjectPresignRequest);
        final URL presignedUrl = presignedPutObjectRequest.url();

        return PreSignedUrlFindResponse.builder()
                .preSignedUrl(presignedUrl.toExternalForm())
                .build();
    }

    private PutObjectRequest generatePutObjectRequest(final PreSignedUrlFindRequest presignedUrlFindRequest,
                                                      final String userId) {
        final String fileName = StringUtils.stripFilenameExtension(presignedUrlFindRequest.getFileName());
        if (!fileName.matches("^[^/\\s]+$")) {
            throw new ApiException(ErrorCode.INVALID_FILENAME);
        }

        final String extension = StringUtils.getFilenameExtension(presignedUrlFindRequest.getFileName());
        if (extension == null) {
            throw new ApiException(ErrorCode.INVALID_EXTENSION);
        }

        if (!extension.matches("^(jpg|jpeg|png|webp)$")) {
            throw new ApiException(ErrorCode.UNSUPPORTED_EXTENSION);
        }

        final UUID uuid = UUID.randomUUID();
        final String objectKey = "user/" + userId + "/" + uuid + "_" + fileName;

        return PutObjectRequest.builder()
                .bucket(bucket)
                .key(objectKey)
                .contentType("image/" + (extension.equals("jpg") ? "jpeg" : extension))
                .build();
    }
}
