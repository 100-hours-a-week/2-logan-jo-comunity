package KBT2.comunity.back.dto.User;

import KBT2.comunity.back.entity.User;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private UUID id;
    private String email;
    private String nickname;
    private String logoImage;

    public static UserDto fromEntity(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .logoImage(user.getLogoImage())
                .build();
    }
}
