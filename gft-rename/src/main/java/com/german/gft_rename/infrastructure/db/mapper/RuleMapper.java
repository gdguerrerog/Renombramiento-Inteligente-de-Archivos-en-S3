package com.german.gft_rename.infrastructure.db.mapper;

import com.german.gft_rename.domain.Rule;
import com.german.gft_rename.infrastructure.db.entity.RuleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RuleMapper {

    Rule toRule(final RuleEntity ruleEntity);

    RuleEntity toEntity(final Rule rule);
}

