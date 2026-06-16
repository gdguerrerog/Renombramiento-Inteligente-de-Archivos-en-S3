package com.german.gft_rename.application.port.out;

import java.util.List;

public interface IFileProvider {

    List<String> listFiles(String folder);
    void copyFile(String folderOriginal, String fileNameOriginal, String folderTarget,  String fileNameTarget);
    void deleteFile(String folder, String fileName);
    boolean fileExists(String folder, String fileName);
}
