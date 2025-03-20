package io.github.scitia.susieserver.comment.service;

import io.github.scitia.susieserver.comment.dto.CommentMRO;
import io.github.scitia.susieserver.comment.dto.CommentDTO;

import java.util.List;

public interface CommentService {

    List<CommentDTO> getAllCommentsForIssueID(Integer issueID);
    CommentDTO addCommentToIssue(CommentMRO comment);
    CommentDTO updateComment(CommentMRO comment);
    void deleteComment(Integer commentID);
}
