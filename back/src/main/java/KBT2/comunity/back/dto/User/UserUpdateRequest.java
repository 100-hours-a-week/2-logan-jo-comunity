package KBT2.comunity.back.dto.User;

import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateRequest {

    @Size(max = 50)
    private String nickname;

    private String logoImage;
}