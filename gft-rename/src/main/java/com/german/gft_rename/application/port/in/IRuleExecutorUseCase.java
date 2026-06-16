package com.german.gft_rename.application.port.in;

import com.german.gft_rename.domain.ExecutionResult;
import com.german.gft_rename.domain.Rule;

import java.time.Instant;
import java.util.List;

public interface IRuleExecutorUseCase {

    ExecutionResult executeRules(String string, Instant date, List<Rule> rules);
}
