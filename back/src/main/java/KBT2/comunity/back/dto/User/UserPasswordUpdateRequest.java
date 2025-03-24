package KBT2.comunity.back.dto.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPasswordUpdateRequest {
    @NotBlank(message = "새 비밀번호를 입력하세요.")
    @Size(min = 6, max = 20)
    private String newPassword;
}