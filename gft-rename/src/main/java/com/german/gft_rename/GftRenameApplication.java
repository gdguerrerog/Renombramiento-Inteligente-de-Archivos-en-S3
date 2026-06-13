package com.german.gft_rename;

import com.german.gft_rename.infrastructure.aws.s3.S3ClientConfiguration;
import com.german.gft_rename.infrastructure.aws.sqs.SQSClientConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
		S3ClientConfiguration.class,
		SQSClientConfiguration.class,
})
public class GftRenameApplication {

	public static void main(String[] args) {
		SpringApplication.run(GftRenameApplication.class, args);
	}

}
