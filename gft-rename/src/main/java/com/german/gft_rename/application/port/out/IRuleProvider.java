package com.german.gft_rename.application.port.out;

import com.german.gft_rename.domain.Rule;
import java.util.List;
import java.util.Optional;

public interface IRuleProvider {

    Rule createRule(final Rule rule);

    Optional<Rule> getRuleById(final Long id);

    List<Rule> getAllRules();

    Rule updateRule(final Rule rule);

    void deleteRuleById(final Long id);
}

