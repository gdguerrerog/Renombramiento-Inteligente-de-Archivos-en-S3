package com.german.gft_rename.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class FileWithStatus {
    private final String name;
    private final String uploadDate;
    private final ExecutionResultType result;
    private final List<RenameExecution> executions;
}
