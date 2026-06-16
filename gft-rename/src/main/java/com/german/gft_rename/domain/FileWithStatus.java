package com.german.gft_rename.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class FileWithStatus {
    private final String name;
    private final Instant uploadDate;
    private final List<RenameExecution> executions;
}
