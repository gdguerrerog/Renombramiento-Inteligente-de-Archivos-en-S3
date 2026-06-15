package com.german.gft_rename.infrastructure.web.controller;

import com.german.gft_rename.application.port.in.IRuleUseCase;
import com.german.gft_rename.domain.Rule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rules")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Rules", description = "APIs for managing business rules")
public class RuleController {

    private final IRuleUseCase ruleUseCase;

    @PostMapping
    @Operation(summary = "Create a new rule", description = "Creates a new business rule with the provided name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rule created successfully",
                    content = @Content(schema = @Schema(implementation = Rule.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Rule> createRule(final @RequestBody Rule rule) {
        log.info("[RuleController] - createRule: API-CALL: Creating rule with name: {}", rule.getName());
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
        log.info("[RuleController] - getRuleById: API-CALL: Retrieving rule with id: {}", id);
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
        log.info("[RuleController] - getAllRules: API-CALL: Retrieving all rules");
        final var rules = ruleUseCase.getAllRules();
        return ResponseEntity.ok(rules);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a rule", description = "Updates an existing rule with new information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rule updated successfully",
                    content = @Content(schema = @Schema(implementation = Rule.class))),
            @ApiResponse(responseCode = "404", description = "Rule not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Rule> updateRule(
            final @PathVariable
            @Parameter(description = "Rule ID", example = "1") Long id,
            final @RequestBody Rule rule) {
        log.info("[RuleController] - updateRule: API-CALL: Updating rule with id: {}", id);
        final var ruleToUpdate = Rule.builder()
                .id(id)
                .name(rule.getName())
                .build();
        final var updatedRule = ruleUseCase.updateRule(ruleToUpdate);
        return ResponseEntity.ok(updatedRule);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a rule", description = "Deletes a specific rule by its identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Rule deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Rule not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteRuleById(
            final @PathVariable
            @Parameter(description = "Rule ID", example = "1") Long id) {
        log.info("[RuleController] - deleteRuleById: API-CALL: Deleting rule with id: {}", id);
        ruleUseCase.deleteRuleById(id);
        return ResponseEntity.noContent().build();
    }
}

