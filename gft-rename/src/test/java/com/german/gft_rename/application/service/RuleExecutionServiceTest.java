package com.german.gft_rename.application.service;

import com.german.gft_rename.domain.ExecutionResult;
import com.german.gft_rename.domain.Rule;
import com.german.gft_rename.domain.RuleType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class RuleExecutionServiceTest {

    @Test
    public void testMatchingPrefix() {
        RuleExecutorService service = new RuleExecutorService();

        Instant date = OffsetDateTime.of(2026, 4, 30, 12, 0, 0, 0, ZoneOffset.UTC).toInstant();

        Rule rule = rule("PHO_CD_DES", "01_Estructura CDT Desmaterializado", RuleType.PREFIX);
        ExecutionResult result = service.executeRules("PHO_CD_DES_AAAAMMDD", date, List.of(rule));
        assertExecutionResult(ExecutionResult.ok("PHO_CD_DES_AAAAMMDD", "01_Estructura CDT Desmaterializado_20260430", rule, null), result);

        rule = rule("PHO_SV", "03_Estructura Cuenta Ahorros", RuleType.PREFIX);
        result = service.executeRules("PHO_SV_AAAAMMDD", date, List.of(rule));
        assertExecutionResult(ExecutionResult.ok("PHO_SV_AAAAMMDD", "03_Estructura Cuenta Ahorros_20260430", rule, null), result);


        rule = rule("activos_", "37_Leasing_", RuleType.PREFIX);
        result = service.executeRules("activos_inmob_bdb_DDMMAAAA.txt", date, List.of(rule));
        assertExecutionResult(ExecutionResult.ok("activos_inmob_bdb_DDMMAAAA.txt", "37_Leasing_inmob_bdb_30042026.txt", rule, null), result);

    }

    @Test
    public void testMatchingSuffix() {
        RuleExecutorService service = new RuleExecutorService();

        Instant date = OffsetDateTime.of(2026, 4, 30, 12, 0, 0, 0, ZoneOffset.UTC).toInstant();

        Rule rule = rule("_SUFFIX1", "_REPLACED_SUFFIX", RuleType.SUFFIX);
        ExecutionResult result = service.executeRules("name_with_SUFFIX1", date, List.of(rule));
        assertExecutionResult(ExecutionResult.ok("name_with_SUFFIX1", "name_with_REPLACED_SUFFIX", null, rule), result);

        rule = rule("_SUFFIX2", "_REPLACED_SUFFIX2", RuleType.SUFFIX);
        result = service.executeRules("_SUFFIX2_SUFFIX2.txt", date, List.of(rule));
        assertExecutionResult(ExecutionResult.ok("_SUFFIX2_SUFFIX2.txt", "_SUFFIX2_REPLACED_SUFFIX2.txt", null, rule), result);

        rule = rule("_SUFFIX3", "_REPLACED_SUFFIX3", RuleType.SUFFIX);
        result = service.executeRules("Some prefix MMAAAADD_SUFFIX3.txt", date, List.of(rule));
        assertExecutionResult(ExecutionResult.ok("Some prefix MMAAAADD_SUFFIX3.txt", "Some prefix 04202630_REPLACED_SUFFIX3.txt", null, rule), result);

    }

    @Test
    public void testMatchingPrefixAndSuffix() {
        RuleExecutorService service = new RuleExecutorService();
        Instant date = OffsetDateTime.of(2026, 4, 30, 12, 0, 0, 0, ZoneOffset.UTC).toInstant();

        Rule prefix = rule("PREFIX_1_", "REPLACED_PREFIX_1_", RuleType.PREFIX);
        Rule suffix = rule("_SUFFIX_1", "_REPLACED_SUFFIX_1", RuleType.SUFFIX);
        ExecutionResult result = service.executeRules("PREFIX_1_other_text_SUFFIX_1", date, List.of(prefix, suffix));
        assertExecutionResult(ExecutionResult.ok("PREFIX_1_other_text_SUFFIX_1", "REPLACED_PREFIX_1_other_text_REPLACED_SUFFIX_1", prefix, suffix), result);


        prefix = rule("PREFIX_2_", "REPLACED_PREFIX_2_", RuleType.PREFIX);
        suffix = rule("_SUFFIX_2", "_REPLACED_SUFFIX_2", RuleType.SUFFIX);
        result = service.executeRules("PREFIX_2_other_text_with_DDMMAAAA_SUFFIX_2", date, List.of(prefix, suffix));
        assertExecutionResult(ExecutionResult.ok("PREFIX_2_other_text_with_DDMMAAAA_SUFFIX_2", "REPLACED_PREFIX_2_other_text_with_30042026_REPLACED_SUFFIX_2", prefix, suffix), result);


        prefix = rule("PREFIX_3_", "REPLACED_PREFIX_3_", RuleType.PREFIX);
        suffix = rule("_SUFFIX_3", "_REPLACED_SUFFIX_3", RuleType.SUFFIX);
        result = service.executeRules("PREFIX_3_DDMMAAAA_SUFFIX_3.ext", date, List.of(prefix, suffix));
        assertExecutionResult(ExecutionResult.ok("PREFIX_3_DDMMAAAA_SUFFIX_3.ext", "REPLACED_PREFIX_3_30042026_REPLACED_SUFFIX_3.ext", prefix, suffix), result);

    }

    @Test
    public void testUnmatched() {
        RuleExecutorService service = new RuleExecutorService();
        Instant date = OffsetDateTime.of(2026, 4, 30, 12, 0, 0, 0, ZoneOffset.UTC).toInstant();

        Rule prefix = rule("PREFIX_1_", "REPLACED_PREFIX_1_", RuleType.PREFIX);
        Rule suffix = rule("_SUFFIX_1", "_REPLACED_SUFFIX_1", RuleType.SUFFIX);
        ExecutionResult result = service.executeRules("_SUFFIX_1 PREFIX_1_", date, List.of(prefix, suffix));
        assertExecutionResult(ExecutionResult.unmatched("_SUFFIX_1 PREFIX_1_"), result);

        result = service.executeRules("Some text with MMDDAAAAA.txt", date, List.of(prefix, suffix));
        assertExecutionResult(ExecutionResult.unmatched("Some text with MMDDAAAAA.txt"), result);



    }

    private Rule rule(String toCheck, String toReplace, RuleType ruleType) {
        return new Rule(
                -1L,
                "",
                0,
                toCheck,
                toReplace,
                ruleType
        );
    }

    private void assertExecutionResult(ExecutionResult expected,  ExecutionResult actual) {
        Assertions.assertEquals(expected.getPrefixRule(), actual.getPrefixRule());
        Assertions.assertEquals(expected.getSuffixRule(), actual.getSuffixRule());
        Assertions.assertEquals(expected.getFinalString(), actual.getFinalString());
        Assertions.assertEquals(expected.getResultType(), actual.getResultType());
        Assertions.assertEquals(expected.getError(), actual.getError());
    }
}
