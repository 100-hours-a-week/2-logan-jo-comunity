package KBT2.comunity.back.service;

import KBT2.comunity.back.dto.Token.TokenDto;
import KBT2.comunity.back.dto.User.*;
import KBT2.comunity.back.entity.User;
import KBT2.comunity.back.exception.code.ConflictException;
import KBT2.comunity.back.exception.code.NotFoundException;
import KBT2.comunity.back.util.message.ErrorMessage;
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
            throw new ConflictException(ErrorMessage.EMAIL_ALREADY_EXISTS);
        } else if (userRepository.existsByNickname(request.getNickname())) {
            throw new ConflictException(ErrorMessage.NICKNAME_ALREADY_EXISTS);
        }
        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .nickname(request.getNickname())
                .logoImage(request.getLogoImage())
                .build();

        userRepository.save(user);
    }

    public TokenDto login(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND));
        return tokenService.generateTokens(user);
    }

    public UserDto getUser(UUID id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND));
        return UserDto.fromEntity(user);
    }

    @Transactional
    public UserDto updateUser(UUID userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND));

        if (request.getNickname() != null && !request.getNickname().isEmpty()) {
            Optional<User> existingUser = userRepository.findByNickname(request.getNickname());
            if (existingUser.isPresent() && !existingUser.get().getId().equals(userId)) {
                throw new ConflictException(ErrorMessage.NICKNAME_ALREADY_EXISTS);
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
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND));

        user.setPassword(request.getNewPassword());
    }

    @Transactional
    public void deleteUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND));
        user.setDeleted(true);
    }
}
