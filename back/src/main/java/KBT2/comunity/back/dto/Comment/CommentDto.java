package KBT2.comunity.back.dto.Comment;

import KBT2.comunity.back.entity.Comment;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NotNull
@AllArgsConstructor
@Builder
public class CommentDto {
    private UUID id;
    private UUID postId;
    private UUID userId;
    private String content;
    private LocalDateTime createdAt;

    public static CommentDto fromEntity(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .postId(comment.getPost().getId())
                .userId(comment.getUser().getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
