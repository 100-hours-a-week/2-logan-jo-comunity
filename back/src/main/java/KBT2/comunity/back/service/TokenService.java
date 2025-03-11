package KBT2.comunity.back.service;

import KBT2.comunity.back.config.jwt.JwtUtil;
import KBT2.comunity.back.dto.TokenResponse;
import KBT2.comunity.back.entity.RefreshToken;
import KBT2.comunity.back.entity.User;
import KBT2.comunity.back.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    public TokenResponse generateTokens(User user) {
        String accessToken = jwtUtil.createAccessToken(user.getId(), user.getEmail());

        Optional<RefreshToken> existingToken = refreshTokenRepository.findByUserId(user.getId());

        String refreshToken = existingToken
                .filter(token -> jwtUtil.validateToken(token.getToken()))
                .map(RefreshToken::getToken)
                .orElseGet(() -> {
                    String newToken = jwtUtil.createRefreshToken(user.getId(), user.getEmail());
                    saveOrUpdateRefreshToken(user, newToken);
                    return newToken;
                });

        return new TokenResponse(accessToken, refreshToken);
    }
    public void saveOrUpdateRefreshToken(User user, String refreshToken) {
        refreshTokenRepository.findByUserId(user.getId())
                .ifPresentOrElse(
                        existingToken -> {
                            existingToken.setToken(refreshToken);
                            existingToken.setExpiryDate(java.time.LocalDateTime.now().plusDays(7));
                            refreshTokenRepository.save(existingToken);
                        },
                        () -> {
                            refreshTokenRepository.save(RefreshToken.builder()
                                    .user(user)
                                    .token(refreshToken)
                                    .expiryDate(java.time.LocalDateTime.now().plusDays(7))
                                    .build());
                        }
                );
    }
}
