package com.german.gft_rename.infrastructure.db.entitymanager;

import com.german.gft_rename.application.port.out.IRenameExecutionProvider;
import com.german.gft_rename.domain.RenameExecution;
import com.german.gft_rename.infrastructure.db.mapper.RenameExecutionMapper;
import com.german.gft_rename.infrastructure.db.repository.RenameExecutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RenameExecutionEntityManager implements IRenameExecutionProvider {

    private final RenameExecutionRepository repository;
    private final RenameExecutionMapper mapper;


    @Override
    public RenameExecution createRenameExecution(RenameExecution renameExecution) {
        final var renameExecutionEntity = mapper.toEntity(renameExecution);
        final var savedRuleEntity = repository.save(renameExecutionEntity);
        return mapper.toRenameExecution(savedRuleEntity);
    }

    @Override
    public List<RenameExecution> getAllRenameExecutions(ListRenameExecutionParams params) {
        Pageable pageable = Pageable.ofSize(params.limit()).withPage(params.page());
        return repository.findByInFileNameContainingAndOutFileNameContaining(params.inputFileName().orElse(""), params.outputFileName().orElse(""), pageable)
                .stream()
                .map(mapper::toRenameExecution)
                .toList();
    }

    @Override
    public List<RenameExecution> getByInName(String inName) {
        return repository.findByInFileName(inName)
                .stream()
                .map(mapper::toRenameExecution)
                .toList();
    }

    @Override
    public List<RenameExecution> getByOutName(String outName) {
        return repository.findByOutFileName(outName)
                .stream()
                .map(mapper::toRenameExecution)
                .toList();
    }


}
