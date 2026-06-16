package com.german.gft_rename.domain;

import com.german.gft_rename.application.port.in.IRuleExecutorUseCase;
import com.german.gft_rename.application.port.out.IRuleProvider;
import lombok.*;

import java.time.Instant;

@RequiredArgsConstructor
@Getter
@Builder
@ToString
public class RenameExecution {
    private final Long id;
    private final Long prefixRuleId;
    private final Long suffixRuleId;
    private final Instant executionTime;
    private final String inFileName;
    private final String outFileName;
    private final ExecutionResultType resultType;
    private final String eventId;
}
