package com.german.gft_rename.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@RequiredArgsConstructor
@Builder
@Schema(name = "Rule", description = "Represents a rule for name processing")
public class Rule {

    @Schema(description = "Unique identifier for the rule", example = "1")
    private final Long id;

    @Schema(description = "Name of the business rule", example = "Replace spaces with underscores")
    private final String name;

    @Schema(description = "Order of the rule", example = "0")
    private final Integer order;

    @Schema(description = "The string to check against", example = "old_name")
    private final String toCheck;

    @Schema(description = "The string to replace with", example = "new_name")
    private final String toReplace;

    @Schema(description = "Rule type (PREFIX, SUFFIX)", example = "PREFIX")
    private final RuleType type;
}

