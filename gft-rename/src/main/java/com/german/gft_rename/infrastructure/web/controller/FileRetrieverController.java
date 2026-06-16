package com.german.gft_rename.infrastructure.web.controller;

import com.german.gft_rename.application.port.in.IFileRetriever;
import com.german.gft_rename.domain.FileWithStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
@Tag(name = "File", description = "APIs for getting data in S3")
public class FileRetrieverController {

    private final IFileRetriever fileRetriever;

    @GetMapping("/to-rename")
    @Operation(summary = "Get files to rename", description = "Retrieves the files in the to-rename folder")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Files found",
                    content = @Content(schema = @Schema(implementation = FileWithStatus.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public List<FileWithStatus> getReceiveFiles() {
        return fileRetriever.getReceiveFiles();
    }

    @GetMapping("/unmatched")
    @Operation(summary = "Get files unmatched", description = "Retrieves the files in the unmatched folder")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Files found",
                    content = @Content(schema = @Schema(implementation = FileWithStatus.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public List<FileWithStatus> getUnmatchedFiles() {
        return fileRetriever.getUnmatchedFiles();
    }

    @GetMapping("/renamed")
    @Operation(summary = "Get files renamed", description = "Retrieves the files in the renamed folder")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Files found",
                    content = @Content(schema = @Schema(implementation = FileWithStatus.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public List<FileWithStatus> getRenamedFiles() {
        return fileRetriever.getRenamedFiles();
    }

}
