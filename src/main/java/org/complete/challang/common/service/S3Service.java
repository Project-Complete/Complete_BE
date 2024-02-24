package org.complete.challang.common.service;

import lombok.RequiredArgsConstructor;
import org.complete.challang.account.user.domain.repository.UserRepository;
import org.complete.challang.common.controller.dto.request.PreSignedUrlGetRequest;
import org.complete.challang.common.controller.dto.response.PreSignedUrlGetResponse;
import org.complete.challang.common.exception.ApiException;
import org.complete.challang.common.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.net.URL;
import java.time.Duration;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class S3Service {

    @Value("${cloud.s3.bucket}")
    private String bucket;

    private final S3Presigner s3Presigner;
    private final UserRepository userRepository;

    public PreSignedUrlGetResponse getPreSignedUrl(final PreSignedUrlGetRequest presignedUrlGetRequest,
                                  final UserDetails userDetails) {
        if (!userRepository.existsById(Long.valueOf(userDetails.getUsername()))) {
            throw new ApiException(ErrorCode.USER_NOT_FOUND);
        }

        final PutObjectRequest putObjectRequest = generatePutObjectRequest(presignedUrlGetRequest, userDetails.getUsername());

        final PutObjectPresignRequest putObjectPresignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))
                .putObjectRequest(putObjectRequest)
                .build();

        final PresignedPutObjectRequest presignedPutObjectRequest = s3Presigner.presignPutObject(putObjectPresignRequest);
        final URL presignedUrl = presignedPutObjectRequest.url();

        return PreSignedUrlGetResponse.builder()
                .preSignedUrl(presignedUrl.toExternalForm())
                .build();
    }

    private PutObjectRequest generatePutObjectRequest(final PreSignedUrlGetRequest presignedUrlGetRequest,
                                                      final String userId) {
        final String fileName = StringUtils.stripFilenameExtension(presignedUrlGetRequest.getFileName());
        if (!fileName.matches("^[^/\\s]+$")) {
            throw new ApiException(ErrorCode.INVALID_FILENAME);
        }

        final String extension = StringUtils.getFilenameExtension(presignedUrlGetRequest.getFileName());
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
