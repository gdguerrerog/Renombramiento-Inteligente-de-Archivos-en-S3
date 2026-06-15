package com.german.gft_rename.application.service;

import com.german.gft_rename.domain.ExecutionResult;
import com.german.gft_rename.domain.Rule;

import java.time.Instant;
import java.util.List;

public interface IRuleExecutor {

    ExecutionResult executeRules(String string, Instant date, List<Rule> rules);
}
