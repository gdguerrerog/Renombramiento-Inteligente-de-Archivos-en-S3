package com.german.gft_rename.application.service;

import com.german.gft_rename.application.port.in.IRuleUseCase;
import com.german.gft_rename.application.port.out.IRuleProvider;
import com.german.gft_rename.domain.Rule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RuleService implements IRuleUseCase {

    private final IRuleProvider ruleProvider;

    @Override
    public Rule createRule(final Rule rule) {
        log.info("[RuleService] - createRule: Creating rule with name: {}", rule.getName());
        return ruleProvider.createRule(rule);
    }

    @Override
    public Optional<Rule> getRuleById(final Long id) {
        log.info("[RuleService] - getRuleById: Retrieving rule with id: {}", id);
        return ruleProvider.getRuleById(id);
    }

    @Override
    public List<Rule> getAllRules() {
        log.info("[RuleService] - getAllRules: Retrieving all rules");
        return ruleProvider.getAllRules();
    }

    @Override
    public Optional<Rule> updateRule(Long id, String name, Integer order) {
        return ruleProvider.getRuleById(id).map(rule -> {
            rule.setName(name);
            rule.setOrder(order);
            return ruleProvider.updateRule(rule);
        });
    }

}

