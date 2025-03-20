// Lab1 // Factory Method

package io.github.scitia.susieserver.service;

import io.github.scitia.susieserver.comment.domain.Comment;
import io.github.scitia.susieserver.comment.dto.CommentMRO;
import io.github.scitia.susieserver.comment.factory.DefaultCommentFactory;
import io.github.scitia.susieserver.user.dto.UserDTO;
import io.github.scitia.susieserver.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static io.github.scitia.susieserver.builder.UserBuilder.createCurrentLoggedInUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class CommentFactoryTest {

    @Mock
    private UserService userService;

    private DefaultCommentFactory commentFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        commentFactory = new DefaultCommentFactory(userService);

        UserDTO currentUser = createCurrentLoggedInUser();
        when(userService.getCurrentLoggedUser()).thenReturn(currentUser);
        when(userService.getUserSafely(currentUser.getUuid())).thenReturn(currentUser);
    }

    @Test
    void createComment_shouldReturnValidComment() {
        // given
        String commentBody = "To jest testowy komentarz";
        CommentMRO commentMRO = new CommentMRO();
        commentMRO.setBody(commentBody);

        // when
        Comment result = commentFactory.createComment(commentMRO);

        // then
        assertNotNull(result);
        assertEquals(commentBody, result.getBody());
        assertNotNull(result.getCreatedBy());
        assertNotNull(result.getCreatedDate());
        assertNotNull(result.getLastModifiedBy());
        assertNotNull(result.getLastModifiedDate());
    }

    @Test
    void createComment_fromString_shouldReturnValidComment() {
        // given
        String commentBody = "To jest testowy komentarz ze stringa";

        // when
        Comment result = commentFactory.createComment(commentBody);

        // then
        assertNotNull(result);
        assertEquals(commentBody, result.getBody());
        assertNotNull(result.getCreatedBy());
        assertNotNull(result.getCreatedDate());
    }

    @Test
    void createSystemComment_shouldHaveSystemPrefix() {
        // given
        String message = "Status zgłoszenia został zmieniony";

        // when
        Comment systemComment = commentFactory.createSystemComment(message);

        // then
        assertNotNull(systemComment);
        assertTrue(systemComment.getBody().startsWith("[SYSTEM]"));
        assertTrue(systemComment.getBody().contains(message));
        assertEquals("system", systemComment.getCreatedBy());
    }

    @Test
    void createReplyComment_shouldHaveReplyFormat() {
        // given
        String commentBody = "To jest odpowiedź";
        CommentMRO commentMRO = new CommentMRO();
        commentMRO.setBody(commentBody);

        UserDTO originalUser = createCurrentLoggedInUser();
        Comment parentComment = new Comment();
        parentComment.setCreatedBy(originalUser.getUuid());

        when(userService.getUserSafely(originalUser.getUuid())).thenReturn(originalUser);

        // when
        Comment replyComment = commentFactory.createReplyComment(commentMRO, parentComment);

        // then
        assertNotNull(replyComment);
        assertTrue(replyComment.getBody().startsWith("RE:"));
        assertTrue(replyComment.getBody().contains("@" + originalUser.getFirstName()));
        assertTrue(replyComment.getBody().contains(commentBody));
    }
}