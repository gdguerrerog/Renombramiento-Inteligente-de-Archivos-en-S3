package com.german.gft_rename.application.service;

import com.german.gft_rename.application.port.in.IFileRetriever;
import com.german.gft_rename.application.port.out.IFileProvider;
import com.german.gft_rename.application.port.out.IRenameExecutionProvider;
import com.german.gft_rename.domain.FileData;
import com.german.gft_rename.domain.FileWithStatus;
import com.german.gft_rename.domain.RenameExecution;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileRetrieverService implements IFileRetriever {

    @Value("${app.general.receive-folder-name}")
    private String receiveFolderName;
    @Value("${app.general.renamed-folder-name}")
    private String renamedFolderName;
    @Value("${app.general.unmatched-folder-name}")
    private String unmatchedFolderName;

    private final IFileProvider fileProvider;
    private final IRenameExecutionProvider renameExecutionProvider;

    @Override
    public List<FileWithStatus> getReceiveFiles() {
        return fileProvider.listFiles(receiveFolderName)
                .stream()
                .filter(f -> !f.getName().isBlank())
                .map(f -> toFileWithStatus(f, renameExecutionProvider.getByInName(f.getName())))
                .toList();
    }



    @Override
    public List<FileWithStatus> getUnmatchedFiles() {
        return fileProvider.listFiles(unmatchedFolderName)
                .stream()
                .filter(f -> !f.getName().isBlank())
                .map(f -> toFileWithStatus(f, renameExecutionProvider.getByInName(f.getName())))
                .toList();
    }

    @Override
    public List<FileWithStatus> getRenamedFiles() {

        return fileProvider.listFiles(renamedFolderName)
                .stream()
                .filter(f -> !f.getName().isBlank())
                .map(f -> toFileWithStatus(f, renameExecutionProvider.getByOutName(f.getName())))
                .toList();
    }

    private FileWithStatus toFileWithStatus(FileData fileData, List<RenameExecution> execution) {
        return new FileWithStatus(fileData.getName(), fileData.getCreationDate(), execution);
    }
}
