package com.german.gft_rename.infrastructure.db.repository;

import com.german.gft_rename.infrastructure.db.entity.RenameExecutionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RenameExecutionRepository extends JpaRepository<RenameExecutionEntity, Long> {

    Page<RenameExecutionEntity> findByInFileNameContainingAndOutFileNameContaining(String inFileName, String outFileName, Pageable pageable);
    List<RenameExecutionEntity> findByInFileName(String inFileName);
    List<RenameExecutionEntity> findByOutFileName(String outFileName);
}
