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
    private final Optional<Exception> error;
    private final Optional<Rule> prefixRule;
    private final Optional<Rule> suffixRule;

    public static ExecutionResult ok(String initialString, String finalString, Rule prefixRule, Rule suffixRule) {
        return ExecutionResult.builder()
                .initialString(initialString)
                .resultType(ExecutionResultType.SUCCESS)
                .finalString(Optional.of(finalString))
                .error(Optional.empty())
                .prefixRule(Optional.ofNullable(prefixRule))
                .suffixRule(Optional.ofNullable(suffixRule))
                .build();
    }

    public static ExecutionResult error(String initialString, Exception error) {
        return ExecutionResult.builder()
                .initialString(initialString)
                .resultType(ExecutionResultType.ERROR)
                .finalString(Optional.empty())
                .error(Optional.of(error))
                .prefixRule(Optional.empty())
                .suffixRule(Optional.empty())
                .build();
    }

    public static ExecutionResult unmatched(String initialString) {
        return ExecutionResult.builder()
                .initialString(initialString)
                .resultType(ExecutionResultType.UNMATCHED)
                .finalString(Optional.of(initialString))
                .error(Optional.empty())
                .prefixRule(Optional.empty())
                .suffixRule(Optional.empty())
                .build();
    }
}
