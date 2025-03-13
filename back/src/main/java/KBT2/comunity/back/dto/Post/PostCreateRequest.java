package KBT2.comunity.back.dto.Post;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCreateRequest {
    private String title;
    private String content;
    private String image;
}
