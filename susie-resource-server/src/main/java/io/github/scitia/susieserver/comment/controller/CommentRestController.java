package io.github.scitia.susieserver.comment.controller;

import io.github.scitia.susieserver.comment.dto.CommentMRO;
import io.github.scitia.susieserver.comment.service.CommentService;
import io.github.scitia.susieserver.comment.dto.CommentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/comment")
@RequiredArgsConstructor
public class CommentRestController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDTO> addCommentToIssue(@RequestBody CommentMRO comment) {
        return ResponseEntity.ok(commentService.addCommentToIssue(comment));
    }

    @PutMapping
    public ResponseEntity<CommentDTO> updateComment(@RequestBody CommentMRO comment) {
        return ResponseEntity.ok(commentService.updateComment(comment));
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Integer id) {
        commentService.deleteComment(id);
    }
}
