package com.german.gft_rename.infrastructure.web.controller;

import com.german.gft_rename.application.port.in.IRuleUseCase;
import com.german.gft_rename.domain.Rule;
import com.german.gft_rename.infrastructure.web.dto.CreateRuleDto;
import com.german.gft_rename.infrastructure.web.dto.UpdateRuleDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rules")
@RequiredArgsConstructor
@Tag(name = "Rules", description = "APIs for managing renaming rules")
public class RuleController {

    private final IRuleUseCase ruleUseCase;

    @PostMapping
    @Operation(summary = "Create a new rule", description = "Creates a new renaming rule")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rule created successfully",
                    content = @Content(schema = @Schema(implementation = Rule.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Rule> createRule(final @RequestBody CreateRuleDto createRule) {
        final Rule rule = Rule.builder()
                .order(createRule.getOrder())
                .name(createRule.getName())
                .type(createRule.getType())
                .toReplace(createRule.getToReplace())
                .toCheck(createRule.getToCheck())
                .build();

        final var createdRule = ruleUseCase.createRule(rule);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRule);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get rule by ID", description = "Retrieves a specific rule by its identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rule found",
                    content = @Content(schema = @Schema(implementation = Rule.class))),
            @ApiResponse(responseCode = "404", description = "Rule not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Rule> getRuleById(
            final @PathVariable
            @Parameter(description = "Rule ID", example = "1") Long id) {
        return ruleUseCase.getRuleById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Get all rules", description = "Retrieves a list of all available business rules")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rules retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Rule.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Rule>> getAllRules() {
        final var rules = ruleUseCase.getAllRules();
        return ResponseEntity.ok(rules);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates a rule", description = "Sets the name and description of the rule")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rules retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Rule.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Rule> updateRule(final @RequestBody UpdateRuleDto updateRule, final @PathVariable Long id) {
        final var rule = ruleUseCase.updateRule(id, updateRule.getName(), updateRule.getOrder());
        return rule.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

}

