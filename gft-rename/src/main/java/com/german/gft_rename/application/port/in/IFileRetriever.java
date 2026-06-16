package com.german.gft_rename.application.port.in;

import com.german.gft_rename.domain.FileWithStatus;

import java.util.List;

public interface IFileRetriever {
    List<FileWithStatus> getToRenameFiles();
    List<FileWithStatus> getUnmatchedFiles();
    List<FileWithStatus> getRenamedFiles();
}
