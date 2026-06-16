package com.german.gft_rename.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "UpdateRuleDto", description = "Update rule")
@Getter
@Setter
public class UpdateRuleDto {
    @Schema(description = "Name of the rename rule", example = "Replace spaces with underscores")
    private String name;

    @Schema(description = "Order of the rule", example = "0")
    private Integer order;

}
