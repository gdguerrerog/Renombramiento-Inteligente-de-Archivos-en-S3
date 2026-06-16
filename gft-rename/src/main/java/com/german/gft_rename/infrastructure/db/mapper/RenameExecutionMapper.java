package com.german.gft_rename.infrastructure.db.mapper;

import com.german.gft_rename.domain.RenameExecution;
import com.german.gft_rename.infrastructure.db.entity.RenameExecutionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RenameExecutionMapper {
    RenameExecution toRenameExecution(final RenameExecutionEntity entity);
    RenameExecutionEntity toEntity(final RenameExecution renameExecution);
}
