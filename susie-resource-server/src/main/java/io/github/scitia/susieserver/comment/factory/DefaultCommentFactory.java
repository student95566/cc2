// Lab1 // Factory Method

package io.github.scitia.susieserver.comment.factory;

import io.github.scitia.susieserver.comment.domain.Comment;
import io.github.scitia.susieserver.comment.dto.CommentMRO;
import io.github.scitia.susieserver.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
@RequiredArgsConstructor
public class DefaultCommentFactory implements CommentFactory {

    private final UserService userService;

    @Override
    public Comment createComment(CommentMRO commentMRO) {
        Comment comment = Comment.builder()
                .body(commentMRO.getBody())
                .build();

        String currentUserUUID = userService.getCurrentLoggedUser().getUuid();
        Date now = new Date();

        comment.setCreatedBy(currentUserUUID);
        comment.setCreatedDate(now);
        comment.setLastModifiedBy(currentUserUUID);
        comment.setLastModifiedDate(now);

        return comment;
    }

    @Override
    public Comment createComment(String body) {
        CommentMRO commentMRO = new CommentMRO();
        commentMRO.setBody(body);
        return createComment(commentMRO);
    }


    public Comment createReplyComment(CommentMRO commentMRO, Comment parentComment) {
        Comment reply = createComment(commentMRO);


        String replyPrefix = "RE: ";
        String originalAuthor = userService.getUserSafely(parentComment.getCreatedBy()).getFirstName();

        if (originalAuthor != null) {
            reply.setBody(replyPrefix + "[@" + originalAuthor + "] " + reply.getBody());
        } else {
            reply.setBody(replyPrefix + reply.getBody());
        }

        return reply;
    }


    public Comment createSystemComment(String message) {
        Comment systemComment = Comment.builder()
                .body("[SYSTEM] " + message)
                .build();

        String systemUserID = "system";
        Date now = new Date();

        systemComment.setCreatedBy(systemUserID);
        systemComment.setCreatedDate(now);
        systemComment.setLastModifiedBy(systemUserID);
        systemComment.setLastModifiedDate(now);

        return systemComment;
    }
}