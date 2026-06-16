package com.german.gft_rename.infrastructure.db.repository;

import com.german.gft_rename.infrastructure.db.entity.RenameExecutionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RenameExecutionRepository extends JpaRepository<RenameExecutionEntity, Long> {

    Page<RenameExecutionEntity> findByInFileNameContainingAndOutFileNameContaining(String inFileName, String outFileName, Pageable pageable);
    Optional<RenameExecutionEntity> findTopByEventIdOrderByExecutionTimeDesc(String eventId);
}
