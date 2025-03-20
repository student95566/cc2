package io.github.scitia.susieserver.issue.domain;

import io.github.scitia.susieserver.comment.domain.Comment;
import io.github.scitia.susieserver.dictionary.domain.IssuePriority;
import io.github.scitia.susieserver.dictionary.domain.IssueStatus;
import io.github.scitia.susieserver.dictionary.domain.IssueType;
import io.github.scitia.susieserver.shared.Auditable;
import io.github.scitia.susieserver.shared.interfaces.Prototype;
import io.github.scitia.susieserver.sprint.domain.Sprint;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Issue extends Auditable implements Serializable, Prototype<Issue> {

    @Id
    @Column(name = "IssueID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_seq_gen")
    @SequenceGenerator(name = "issue_seq_gen", sequenceName = "issue_seq")
    private Integer id;

    private String name;

    private String description;

    private Integer estimation;

    private String reporterID;

    private String assigneeID;

    private Integer projectID;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "SprintID")
    private Sprint sprint;

    @ManyToOne
    @JoinColumn(name = "Issue_StatusID")
    private IssueStatus issueStatus;

    @ManyToOne
    @JoinColumn(name = "Issue_TypeID")
    private IssueType issueType;

    @ManyToOne
    @JoinColumn(name = "Issue_PriorityID")
    private IssuePriority issuePriority;

    /**
     * Klonowanie obiektu zgłoszenia ma sens jeśli nie będzie zaiwerał komentarzy,
     * sprintu oraz ID. Jest to związane z logiką bizensową projektu.
     */
    public Issue(Issue issue) {
        this.name = issue.name;
        this.description = issue.description;
        this.estimation = issue.estimation;
        this.reporterID = issue.reporterID;
        this.assigneeID = issue.assigneeID;
        this.projectID = issue.projectID;
        this.issueStatus = issue.issueStatus;
        this.issueType = issue.issueType;
        this.issuePriority = issue.issuePriority;
    }

    @Override
    public Issue cloneObject() {
        return new Issue(this);
    }
}
