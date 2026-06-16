package com.german.gft_rename.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
@Getter
public class FileData {
    private final String name;
    private final Instant creationDate;
}
