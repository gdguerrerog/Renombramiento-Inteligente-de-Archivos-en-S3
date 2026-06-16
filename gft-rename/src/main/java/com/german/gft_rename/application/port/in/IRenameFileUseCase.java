package com.german.gft_rename.application.port.in;

import com.german.gft_rename.domain.ExecutionResult;
import com.german.gft_rename.domain.FileData;
import com.german.gft_rename.domain.RenameExecution;

import java.util.List;

public interface IRenameFileUseCase {


    List<RenameExecution> renameFiles(List<FileData> files);
}
