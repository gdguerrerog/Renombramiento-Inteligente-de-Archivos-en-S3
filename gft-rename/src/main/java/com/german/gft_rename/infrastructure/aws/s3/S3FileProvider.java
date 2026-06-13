package com.german.gft_rename.infrastructure.aws.s3;

import com.german.gft_rename.application.port.out.IFileProvider;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.util.List;

@Component
@RequiredArgsConstructor
public class S3FileProvider implements IFileProvider {

    private static final Logger log = LoggerFactory.getLogger(S3FileProvider.class);
    private final S3Client s3Client;
    @Value("${app.aws.bucket-name}") private String bucketName;

    @Override
    public List<String> listFiles(String folder) {
        ListObjectsV2Request request =
                ListObjectsV2Request.builder()
                        .bucket(bucketName)
                        .build();

        ListObjectsV2Response response =
                s3Client.listObjectsV2(request);

        return response.contents()
                .stream()
                .map(S3Object::key)
                .toList();
    }

}
