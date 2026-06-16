package com.german.gft_rename.application.port.in;

import com.german.gft_rename.domain.FileWithStatus;

import java.util.List;

public interface IFileRetriever {
    List<FileWithStatus> getReceiveFiles();
    List<FileWithStatus> getUnmatchedFiles();
    List<FileWithStatus> getRenamedFiles();
}
