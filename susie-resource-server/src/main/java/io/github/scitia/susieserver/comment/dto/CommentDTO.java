package io.github.scitia.susieserver.comment.dto;

import io.github.scitia.susieserver.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private Integer commentID;
    private String body;
    private UserDTO author;
}
