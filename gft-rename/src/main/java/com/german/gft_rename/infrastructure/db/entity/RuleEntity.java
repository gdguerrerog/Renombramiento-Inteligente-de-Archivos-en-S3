package com.german.gft_rename.infrastructure.db.entity;

import com.german.gft_rename.domain.RuleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RuleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer order;
    private String toCheck;
    private String toReplace;

    @Enumerated(EnumType.STRING)
    private RuleType type;
}

