package com.german.gft_rename.infrastructure.db.entitymanager;

import com.german.gft_rename.application.port.out.IRuleProvider;
import com.german.gft_rename.domain.Rule;
import com.german.gft_rename.infrastructure.db.mapper.RuleMapper;
import com.german.gft_rename.infrastructure.db.repository.RuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RuleEntityManager implements IRuleProvider {

    private final RuleRepository ruleRepository;
    private final RuleMapper ruleMapper;

    @Override
    public Rule createRule(final Rule rule) {
        final var ruleEntity = ruleMapper.toEntity(rule);
        final var savedRuleEntity = ruleRepository.save(ruleEntity);
        return ruleMapper.toRule(savedRuleEntity);
    }

    @Override
    public Optional<Rule> getRuleById(final Long id) {
        return ruleRepository.findById(id).map(ruleMapper::toRule);
    }

    @Override
    public List<Rule> getAllRules() {
        return ruleRepository.findAll().stream()
                .map(ruleMapper::toRule)
                .toList();
    }

    @Override
    public Rule updateRule(final Rule rule) {
        final var ruleEntity = ruleMapper.toEntity(rule);
        final var updatedRuleEntity = ruleRepository.save(ruleEntity);
        return ruleMapper.toRule(updatedRuleEntity);
    }

    @Override
    public void deleteRuleById(final Long id) {
        ruleRepository.deleteById(id);
    }
}

