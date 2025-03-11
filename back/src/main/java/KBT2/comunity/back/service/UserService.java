package KBT2.comunity.back.service;

import KBT2.comunity.back.config.jwt.JwtUtil;
import KBT2.comunity.back.dto.TokenResponse;
import KBT2.comunity.back.dto.User.*;
import KBT2.comunity.back.entity.RefreshToken;
import KBT2.comunity.back.entity.User;
import KBT2.comunity.back.repository.RefreshTokenRepository;
import KBT2.comunity.back.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Transactional
    public void singUp(UserCreateRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        } else if (userRepository.existsByNickname(request.getNickname())) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }
        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .nickname(request.getNickname())
                .logoImage(request.getLogoImage())
                .build();

        userRepository.save(user);
    }

    public TokenResponse login(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));
        return tokenService.generateTokens(user);
    }

    public UserDto getUser(UUID id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        return UserDto.fromEntity(user);
    }

    @Transactional
    public UserDto updateUser(UUID userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (request.getNickname() != null && !request.getNickname().isEmpty()) {
            Optional<User> existingUser = userRepository.findByNickname(request.getNickname());
            if (existingUser.isPresent() && !existingUser.get().getId().equals(userId)) {
                throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
            }
            user.setNickname(request.getNickname());
        }

        if (request.getLogoImage() != null) {
            user.setLogoImage(request.getLogoImage());
        }

        return UserDto.fromEntity(user);
    }

    @Transactional
    public void updatePassword(UUID userId, UserPasswordUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        user.setPassword(request.getNewPassword());
    }

    @Transactional
    public void deleteUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        user.setDeleted(true);
    }
}
