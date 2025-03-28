package KBT2.comunity.back.service;

import KBT2.comunity.back.dto.Token.TokenDto;
import KBT2.comunity.back.dto.User.*;
import KBT2.comunity.back.entity.User;
import KBT2.comunity.back.exception.code.ConflictException;
import KBT2.comunity.back.exception.code.NotFoundException;
import KBT2.comunity.back.exception.code.UnauthorizedException;
import KBT2.comunity.back.repository.UserRepository;
import KBT2.comunity.back.util.message.ErrorMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final ImageUploadService imageUploadService;

    @Transactional
    public void singUp(UserCreateRequest request) {
        System.out.println(request);
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException(ErrorMessage.EMAIL_ALREADY_EXISTS);
        } else if (userRepository.existsByNickname(request.getNickname())) {
            throw new ConflictException(ErrorMessage.NICKNAME_ALREADY_EXISTS);
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        String imageUrl = null;
        MultipartFile logoImageFile = request.getLogoImage();
        if (logoImageFile != null && !logoImageFile.isEmpty()) {
            try {
                imageUrl = imageUploadService.uploadToImgbb(logoImageFile);
            } catch (IOException e) {
                throw new RuntimeException("이미지 업로드 실패", e);
            }
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .nickname(request.getNickname())
                .logoImage(imageUrl)
                .build();

        userRepository.save(user);
    }

    public TokenDto login(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException(ErrorMessage.INVALID_PASSWORD);
        }

        return tokenService.generateTokens(user);
    }

    public UserDto getUser(UUID id) {
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
            String imageUrl = null;
            MultipartFile logoImageFile = request.getLogoImage();
            if (!logoImageFile.isEmpty()) {
                try {
                    imageUrl = imageUploadService.uploadToImgbb(logoImageFile);
                } catch (IOException e) {
                    throw new RuntimeException("이미지 업로드 실패", e);
                }
            }
            System.out.println(imageUrl);

            user.setLogoImage(imageUrl);
        }
        userRepository.save(user);
        return UserDto.fromEntity(user);
    }

    @Transactional
    public void updatePassword(UUID userId, UserPasswordUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND));

        String encodedPassword = passwordEncoder.encode(request.getNewPassword());

        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND));
        user.setDeleted(true);
        userRepository.save(user);
    }
}
