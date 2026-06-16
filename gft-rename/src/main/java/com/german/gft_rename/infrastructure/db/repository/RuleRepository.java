package com.german.gft_rename.infrastructure.db.repository;

import com.german.gft_rename.infrastructure.db.entity.RuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface RuleRepository extends JpaRepository<RuleEntity, Long> {
}

