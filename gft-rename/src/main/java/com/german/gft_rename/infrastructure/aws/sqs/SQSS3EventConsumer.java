package com.german.gft_rename.infrastructure.aws.sqs;

import org.springframework.stereotype.Component;
import io.awspring.cloud.sqs.annotation.SqsListener;

@Component
public class SQSS3EventConsumer {

    @SqsListener("${app.aws.sqs-queue-name}")
    public void receiveMessage(String message) {
        System.out.println(message);
    }
}
