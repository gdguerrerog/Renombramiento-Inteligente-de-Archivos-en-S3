package com.german.gft_rename.application.service;

import com.german.gft_rename.application.port.in.IRuleExecutorUseCase;
import com.german.gft_rename.domain.ExecutionResult;
import com.german.gft_rename.domain.Rule;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class RuleExecutorService implements IRuleExecutorUseCase {

    private static final Pattern DATE_PATTERN = Pattern.compile("(AAAA|MM|DD){3}");


    @Override
    public ExecutionResult executeRules(String string, Instant date, List<Rule> rules) {
        String[] nameAndExt = getFileNameAndExtention(string);
        String name = nameAndExt[0];
        String ext = nameAndExt[1];

        ApplyRulesDto rulesApplied = applyRules(name, rules);

        if (Objects.isNull(rulesApplied.prefixRule()) && Objects.isNull(rulesApplied.suffixRule())) return ExecutionResult.unmatched(string);

        String nameWithRulesAndDate = dateTransform(rulesApplied.name, date);

        return ExecutionResult.ok(string ,nameWithRulesAndDate + ext, rulesApplied.prefixRule(),  rulesApplied.suffixRule());
    }

    private static ApplyRulesDto applyRules(String name, List<Rule> rules) {
        Rule prefixRule = null;
        Rule suffixRule = null;
        for (Rule rule : rules) {
            switch (rule.getType()) {
                case PREFIX: {
                    if (Objects.nonNull(prefixRule)) break;
                    if (!name.startsWith(rule.getToCheck())) break;

                    prefixRule = rule;
                    name = rule.getToReplace() + name.substring(rule.getToCheck().length());

                    break;
                }
                case SUFFIX: {
                    if (Objects.nonNull(suffixRule)) break;
                    if (!name.endsWith(rule.getToCheck())) break;

                    name = name.substring(0, name.length() - rule.getToCheck().length()) + rule.getToReplace();
                    suffixRule = rule;

                    break;
                }
            }

        }


        return new ApplyRulesDto(name, prefixRule, suffixRule);
    }


    private static String dateTransform(String name, Instant date) {
        return DATE_PATTERN.matcher(name).replaceAll(result -> {
            String group = result.group();
            String format = group
                    .replace("AAAA", "yyyy")
                    .replace("MM", "MM")
                    .replace("DD", "dd");

            return DateTimeFormatter.ofPattern(format).withZone(ZoneOffset.UTC).format(date);
        });

    }

    private String[] getFileNameAndExtention(String name) {
        String[] parts = name.split("\\.");
        if (parts.length == 1) return new String[] {name, ""};
        StringBuilder nameNoExt = new StringBuilder();
        for (int i = 0; i < parts.length - 1; i++) nameNoExt.append(parts[i]);
        return new String[] {nameNoExt.toString(), "." + parts[parts.length - 1]};
    }

    private record ApplyRulesDto(String name, Rule prefixRule, Rule suffixRule) {}

}

