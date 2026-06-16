package com.german.gft_rename.application.service;

import com.german.gft_rename.application.port.in.IRenameFileUseCase;
import com.german.gft_rename.application.port.in.IRuleExecutorUseCase;
import com.german.gft_rename.application.port.out.IFileProvider;
import com.german.gft_rename.application.port.out.IRenameExecutionProvider;
import com.german.gft_rename.application.port.out.IRuleProvider;
import com.german.gft_rename.domain.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RenameFileService implements IRenameFileUseCase {

    private static final Logger log = LoggerFactory.getLogger(RenameFileService.class);

    @Value("${app.general.receive-folder-name}")
    private String receiveFolderName;
    @Value("${app.general.renamed-folder-name}")
    private String renamedFolderName;
    @Value("${app.general.unmatched-folder-name}")
    private String unmatchedFolderName;

    private final IRuleProvider ruleProvider;
    private final IRuleExecutorUseCase ruleExecution;
    private final IRenameExecutionProvider renameExecutionProvider;
    private final IFileProvider fileProvider;

    @Override
    public List<RenameExecution> renameFiles(List<FileData> files) {
        List<Rule> rules = ruleProvider.getAllRules();
        return files.stream().map(f -> processFile(f, rules)).toList();
    }

    private RenameExecution processFile(FileData data, List<Rule> rules) {
        Optional<RenameExecution> checkAlready = renameExecutionProvider.getByEventId(data.getEventId());

        // Check if is a retry event
        if (checkAlready.isPresent()) {
            switch (checkAlready.get().getResultType()) {
                case SUCCESS: return checkAlready.get();
                case DELETE_FAILED:
                case UNMATCHED_DELETE_FAILED:
                    return retryDelete(checkAlready.get());
            }
        }

        // Apply rules
        ExecutionResult ruleResult = ruleExecution.executeRules(data.getName(), data.getCreationDate(), rules);

        // Update S3
        ExecutionResult uploadResult = switch (ruleResult.getResultType()) {
            case SUCCESS -> moveToRenamed(ruleResult);
            case UNMATCHED -> moveToUnmatched(ruleResult);
            default -> throw new IllegalStateException("Unexpected value: " + ruleResult.getResultType());
        };

        // Store result
        RenameExecution execution = createRenameExecution(uploadResult);
        return renameExecutionProvider.createRenameExecution(execution);
    }

    private ExecutionResult moveToRenamed(final ExecutionResult result) {
        log.info("Moving to renamed folder");
        if(!copy(receiveFolderName, result.getInitialString(), renamedFolderName, result.getFinalString().orElseThrow())) {
            return ExecutionResult.error(result.getInitialString());
        }

        if (deleteIdempotent(receiveFolderName, result.getInitialString())) {
            return result;
        } else {
            return ExecutionResult.copyFailed(result);
        }
    }

    private ExecutionResult moveToUnmatched(final ExecutionResult result) {
        log.info("Moving to unmatched files");
        if(!copy(receiveFolderName, result.getInitialString(), unmatchedFolderName, result.getFinalString().orElseThrow())) {
            return ExecutionResult.unmatched(result.getInitialString());
        }

        if (deleteIdempotent(receiveFolderName, result.getInitialString())) {
            log.info("Success movement to unmatched folder");
            return result;
        } else {
            log.info("Error deleting source");
            return ExecutionResult.copyFailed(result);
        }
    }

    private boolean copy(String folderOriginal, String fileNameOriginal, String folderTarget,  String fileNameTarget) {
        try {
            fileProvider.copyFile(folderOriginal, fileNameOriginal, folderTarget, fileNameTarget);
            log.info("Success copy file from {} to {}", folderOriginal + "/" + fileNameOriginal, folderTarget + "/" + fileNameTarget);
            return true;
        } catch (Exception ex) {
            log.error("Error copy file from {} to {}", folderOriginal + "/" + fileNameOriginal, folderTarget + "/" + fileNameTarget, ex);
            return false;
        }
    }

    private boolean deleteIdempotent(String folderName, String fileName) {
        try {
            if (fileProvider.fileExists(folderName, fileName)) {
                fileProvider.deleteFile(folderName, fileName);
            }
            log.info("Success delete file {}", folderName + "/" + fileName);
            return true;
        } catch (Exception ex) {
            log.info("Error on delete file {}", folderName + "/" + fileName);
            return false;
        }
    }

    private RenameExecution createRenameExecution(final ExecutionResult result) {
        return RenameExecution.builder()
                .prefixRuleId(result.getPrefixRule().map(Rule::getId).orElse(null))
                .suffixRuleId(result.getSuffixRule().map(Rule::getId).orElse(null))
                .executionTime(Instant.now())
                .inFileName(result.getInitialString())
                .outFileName(result.getFinalString().orElse(null))
                .resultType(result.getResultType())
                .build();
    }



    private RenameExecution retryDelete(RenameExecution previous) {
        ExecutionResultType resultType;

        if (deleteIdempotent(receiveFolderName, previous.getInFileName())) {
            resultType = switch (previous.getResultType()) {
                case DELETE_FAILED -> ExecutionResultType.SUCCESS;
                case UNMATCHED_DELETE_FAILED -> ExecutionResultType.UNMATCHED;
                default -> throw new IllegalStateException("Unexpected value: " + previous.getResultType());
            };
        } else {
            resultType = previous.getResultType();
        }

        RenameExecution execution = RenameExecution.builder()
                .prefixRuleId(previous.getPrefixRuleId())
                .suffixRuleId(previous.getSuffixRuleId())
                .executionTime(Instant.now())
                .inFileName(previous.getInFileName())
                .outFileName(previous.getOutFileName())
                .resultType(resultType)
                .build();

        return renameExecutionProvider.createRenameExecution(execution);

    }
}
