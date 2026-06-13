package com.german.gft_rename.application.port.out;

import java.util.List;

public interface IFileProvider {

    List<String> listFiles(String folder);
}
