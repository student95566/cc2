package io.github.scitia.susieserver.issue.repository;

import io.github.scitia.susieserver.comment.domain.Comment;
import io.github.scitia.susieserver.issue.domain.Issue;
import io.github.scitia.susieserver.sprint.domain.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Integer> {

    List<Issue> findAllBySprint(Sprint sprint);
    List<Issue> findAllByAssigneeID(String assigneeID);
    Issue findByCommentsContaining(Comment comment);
}
