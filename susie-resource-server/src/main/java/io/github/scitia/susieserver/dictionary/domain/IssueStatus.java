package io.github.scitia.susieserver.dictionary.domain;

import io.github.scitia.susieserver.dictionary.service.issue.status.IssueStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class IssueStatus {

    @Id
    @Column(name = "StatusID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_status_seq_gen")
    @SequenceGenerator(name = "issue_status_seq_gen", sequenceName = "issue_status_seq")
    private Integer id;

    @Enumerated(EnumType.STRING)
    private IssueStatusEnum statusName;
}
