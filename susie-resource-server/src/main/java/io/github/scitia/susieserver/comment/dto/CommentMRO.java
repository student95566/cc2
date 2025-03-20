package io.github.scitia.susieserver.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentMRO {

    private Integer commentID;
    private Integer issueID;
    private String body;
}
