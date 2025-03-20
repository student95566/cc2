package io.github.scitia.susieserver.comment.mapper;

import io.github.scitia.susieserver.comment.domain.Comment;
import io.github.scitia.susieserver.comment.dto.CommentDTO;
import io.github.scitia.susieserver.user.dto.UserDTO;
import io.github.scitia.susieserver.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentDTOMapper {

    private final UserService userService;

    public CommentDTO map(Comment from, UserDTO userDTO) {
        return CommentDTO.builder()
                .commentID(from.getId())
                .body(from.getBody())
                .author(userDTO)
                .build();
    }

    public CommentDTO mapV2(Comment from) {
        return CommentDTO.builder()
                .commentID(from.getId())
                .body(from.getBody())
                .author(userService.getUserByUUID(from.getCreatedBy()))
                .build();
    }
}
