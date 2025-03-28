package KBT2.comunity.back.dto.Comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CommentUserInfo {
    private String nickname;
    private String logoImage;
}
