package io.github.scitia.susieserver.comment.service;

import io.github.scitia.susieserver.comment.domain.Comment;
import io.github.scitia.susieserver.comment.dto.CommentDTO;
import io.github.scitia.susieserver.comment.dto.CommentMRO;
import io.github.scitia.susieserver.comment.factory.CommentFactory;
import io.github.scitia.susieserver.comment.mapper.CommentDTOMapper;
import io.github.scitia.susieserver.comment.repository.CommentRepository;
import io.github.scitia.susieserver.issue.domain.Issue;
import io.github.scitia.susieserver.user.service.UserService;
import io.github.scitia.susieserver.user.dto.UserDTO;
import io.github.scitia.susieserver.issue.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static io.github.scitia.susieserver.exception.dictionary.ExceptionMessages.COMMENT_DOES_NOT_EXISTS;
import static io.github.scitia.susieserver.exception.dictionary.ExceptionMessages.FORBIDDEN_COMMENT_ACCESS;
import static io.github.scitia.susieserver.exception.dictionary.ExceptionMessages.ISSUE_DOES_NOT_EXISTS;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UserService userService;
    private final CommentDTOMapper commentDTOMapper;
    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;
    // Lab1 // Factory Method
    private final CommentFactory commentFactory;

    @Override
    public List<CommentDTO> getAllCommentsForIssueID(Integer issueID) {

        Issue issue = issueRepository.findById(issueID)
                .orElseThrow(() -> new RuntimeException(ISSUE_DOES_NOT_EXISTS));

        return issue.getComments()
                .stream()
                .map(commentDTOMapper::mapV2)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDTO addCommentToIssue(CommentMRO comment) {

        UserDTO currentLoggedInUser = userService.getCurrentLoggedUser();
        Issue commentedIssue = issueRepository.findById(comment.getIssueID())
                .orElseThrow(() -> new RuntimeException(ISSUE_DOES_NOT_EXISTS));

        // Lab1 // Factory Method
        /*
        Comment createdComment = Comment.builder()
                .body(comment.getBody())
                .build();
                */
        Comment createdComment = commentFactory.createComment(comment);
        commentRepository.save(createdComment);

        Set<Comment> issueComments = commentedIssue.getComments();
        issueComments.add(createdComment);
        issueRepository.save(commentedIssue);

        return commentDTOMapper.map(createdComment, currentLoggedInUser);
    }

    @Override
    public CommentDTO updateComment(CommentMRO comment) {

        UserDTO currentLoggedInUser = userService.getCurrentLoggedUser();

        Comment updatedComment = commentRepository.findById(comment.getCommentID())
                .orElseThrow(() -> new RuntimeException(COMMENT_DOES_NOT_EXISTS));

        if (updatedComment.getCreatedBy().equals(currentLoggedInUser.getUuid())) {
            updatedComment.setBody(comment.getBody());
            commentRepository.save(updatedComment);
        } else {
            throw new RuntimeException(FORBIDDEN_COMMENT_ACCESS);
        }

        return commentDTOMapper.map(updatedComment, currentLoggedInUser);
    }

    @Override
    public void deleteComment(Integer commentID) {

        UserDTO currentLoggedInUser = userService.getCurrentLoggedUser();

        Comment comment = commentRepository.findById(commentID)
                .orElseThrow(() -> new RuntimeException(COMMENT_DOES_NOT_EXISTS));

        if (comment.getCreatedBy().equals(currentLoggedInUser.getUuid())) {

            Issue updatedIssue = issueRepository.findByCommentsContaining(comment);
            Set<Comment> issueComments = updatedIssue.getComments();
            issueComments.remove(comment);
            issueRepository.save(updatedIssue);

            commentRepository.deleteById(commentID);
        } else {
            throw new RuntimeException(FORBIDDEN_COMMENT_ACCESS);
        }
    }


    public CommentDTO addSystemCommentToIssue(Integer issueID, String message) {
        Issue issue = issueRepository.findById(issueID)
                .orElseThrow(() -> new RuntimeException(ISSUE_DOES_NOT_EXISTS));

        Comment systemComment = commentFactory.createSystemComment(message);
        commentRepository.save(systemComment);

        Set<Comment> issueComments = issue.getComments();
        issueComments.add(systemComment);
        issueRepository.save(issue);

        return commentDTOMapper.map(systemComment, userService.getCurrentLoggedUser());
    }
}
