package KBT2.comunity.back.dto.Post;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponse {
    private UUID id;
}
