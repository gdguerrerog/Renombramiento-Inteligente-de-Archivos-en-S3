package com.german.gft_rename.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class ExecutionResult {
    private final String initialString;
    private final ExecutionResultType resultType;
    private final Optional<String> finalString;
    private final Optional<Rule> prefixRule;
    private final Optional<Rule> suffixRule;

    public static ExecutionResult ok(String initialString, String finalString, Rule prefixRule, Rule suffixRule) {
        return ExecutionResult.builder()
                .initialString(initialString)
                .resultType(ExecutionResultType.SUCCESS)
                .finalString(Optional.of(finalString))
                .prefixRule(Optional.ofNullable(prefixRule))
                .suffixRule(Optional.ofNullable(suffixRule))
                .build();
    }

    public static ExecutionResult error(String initialString) {
        return ExecutionResult.builder()
                .initialString(initialString)
                .resultType(ExecutionResultType.ERROR)
                .finalString(Optional.empty())
                .prefixRule(Optional.empty())
                .suffixRule(Optional.empty())
                .build();
    }

    public static ExecutionResult unmatched(String initialString) {
        return ExecutionResult.builder()
                .initialString(initialString)
                .resultType(ExecutionResultType.UNMATCHED)
                .finalString(Optional.of(initialString))
                .prefixRule(Optional.empty())
                .suffixRule(Optional.empty())
                .build();
    }

    public static ExecutionResult alreadyExecuted(String initialString, String finalString) {
        return ExecutionResult.builder()
                .initialString(initialString)
                .resultType(ExecutionResultType.ALREADY_EXECUTED)
                .finalString(Optional.of(finalString))
                .prefixRule(Optional.empty())
                .suffixRule(Optional.empty())
                .build();
    }

    public static ExecutionResult copyFailed(ExecutionResult previous) {
        return ExecutionResult.builder()
                .initialString(previous.getInitialString())
                .resultType(ExecutionResultType.DELETE_FAILED)
                .finalString(previous.getFinalString())
                .prefixRule(previous.getPrefixRule())
                .suffixRule(previous.getSuffixRule())
                .build();
    }
}
