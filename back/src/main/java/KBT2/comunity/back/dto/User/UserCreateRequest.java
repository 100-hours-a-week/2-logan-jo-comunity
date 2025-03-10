package KBT2.comunity.back.dto.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreateRequest {

    @Email(message = "올바른 이메일 형식이어야 합니다.")
    @NotBlank(message = "이메일을 입력하세요.")
    @Size(max = 25)
    private String email;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Size(min = 6, max = 20)
    private String password;

    @NotBlank(message = "닉네임을 입력하세요.")
    @Size(max = 50)
    private String nickname;

    private String logoImage;
}