package com.german.gft_rename.infrastructure.web.dto;

import com.german.gft_rename.domain.RuleType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "CreateRuleDto", description = "Create rule params")
@Getter
@Setter
public class CreateRuleDto {

    @Schema(description = "Name of the rename rule", example = "Replace spaces with underscores")
    private String name;

    @Schema(description = "Order of the rule", example = "0")
    private Integer order;

    @Schema(description = "The string to check against", example = "old_name")
    private String toCheck;

    @Schema(description = "The string to replace with", example = "new_name")
    private String toReplace;

    @Schema(description = "Rule type (PREFIX, SUFFIX)", example = "PREFIX")
    private RuleType type;
}
