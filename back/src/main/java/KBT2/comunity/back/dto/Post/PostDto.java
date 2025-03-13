package KBT2.comunity.back.dto.Post;

import KBT2.comunity.back.entity.Post;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {
    private UUID id;
    private String title;
    private String content;
    private String image;
    private String author;
    private String authorImage;
    private int likes;
    private int views;
    private String createdAt;
    private String updatedAt;

    public static PostDto fromEntity(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .image(post.getImage())
                .author(post.getUser().getNickname())
                .authorImage(post.getUser().getLogoImage())
                .likes(post.getLikes())
                .views(post.getViews())
                .createdAt(post.getCreatedAt().toString())
                .updatedAt(post.getUpdatedAt().toString())
                .build();
    }
}
