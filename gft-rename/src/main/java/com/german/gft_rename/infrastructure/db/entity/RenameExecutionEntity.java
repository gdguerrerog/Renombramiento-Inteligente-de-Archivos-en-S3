package com.german.gft_rename.infrastructure.db.entity;

import com.german.gft_rename.domain.ExecutionResultType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "rename_execution")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RenameExecutionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long prefixRuleId;
    private Long suffixRuleId;
    private Instant executionTime;
    private String inFileName;
    private String outFileName;
    private String eventId;

    @Enumerated(EnumType.STRING)
    private ExecutionResultType resultType;
}
