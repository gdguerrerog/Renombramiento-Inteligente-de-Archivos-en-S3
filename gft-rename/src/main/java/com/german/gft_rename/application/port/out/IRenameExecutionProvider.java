package com.german.gft_rename.application.port.out;

import com.german.gft_rename.domain.RenameExecution;

import java.util.List;
import java.util.Optional;

public interface IRenameExecutionProvider {

    RenameExecution createRenameExecution(RenameExecution renameExecution);
    List<RenameExecution> getAllRenameExecutions(ListRenameExecutionParams params);
    List<RenameExecution> getByInName(String inName);
    List<RenameExecution> getByOutName(String outName);

    public record ListRenameExecutionParams(Integer page, Integer limit, Optional<String> inputFileName, Optional<String> outputFileName) {}
}

