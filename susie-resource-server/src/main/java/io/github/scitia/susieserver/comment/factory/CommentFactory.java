// Lab1 // Factory Method

package io.github.scitia.susieserver.comment.factory;

import io.github.scitia.susieserver.comment.domain.Comment;
import io.github.scitia.susieserver.comment.dto.CommentMRO;


public interface CommentFactory {

    Comment createComment(CommentMRO commentMRO);

    Comment createComment(String body);

    Comment createSystemComment(String message);
}