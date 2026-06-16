package com.german.gft_rename.infrastructure.aws.sqs;

import com.german.gft_rename.application.port.in.IRenameFileUseCase;
import com.german.gft_rename.application.port.in.IRuleExecutorUseCase;
import com.german.gft_rename.domain.ExecutionResultType;
import com.german.gft_rename.domain.FileData;
import com.german.gft_rename.domain.RenameExecution;
import io.awspring.cloud.sqs.listener.acknowledgement.Acknowledgement;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import io.awspring.cloud.sqs.annotation.SqsListener;
import software.amazon.awssdk.eventnotifications.s3.model.S3EventNotification;
import software.amazon.awssdk.eventnotifications.s3.model.S3EventNotificationRecord;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SQSS3EventConsumer {

    private static final Logger log = LoggerFactory.getLogger(SQSS3EventConsumer.class);

    private final IRenameFileUseCase renameFile;

    @SqsListener(value = "${app.aws.sqs-queue-name}")
    public void receiveMessage(String json, Acknowledgement acknowledgement) {

        log.info("Received SQS message from SQS queue: \n{}", json);

        S3EventNotification event = S3EventNotification.fromJson(json);
        List<S3EventNotificationRecord> records = event.getRecords();
        if (records == null) {
            acknowledgement.acknowledge();
            return;
        }

        List<RenameExecution> execution = renameFile.renameFiles(records.stream().map(r -> {
                String key = r.getS3().getObject().getKey();
                String fileName = key.substring(key.indexOf("/") + 1);
                Instant eventTime = r.getEventTime();

                return new FileData(fileName, eventTime);
            })
            .toList()
        );

        boolean success = execution.stream().allMatch(f -> f.getResultType() == ExecutionResultType.SUCCESS);
        if (success) {
            acknowledgement.acknowledge();
            log.info("Successfully processed SQS message from SQS queue");
        } else {
            log.info("Error on some SQS Messages: \n{}", execution);
        }


        // Do not acknowledge if there are some errors

    }
}
