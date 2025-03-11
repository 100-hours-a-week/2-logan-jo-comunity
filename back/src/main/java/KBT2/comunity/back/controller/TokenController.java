package KBT2.comunity.back.controller;

import KBT2.comunity.back.dto.Token.TokenDto;
import KBT2.comunity.back.dto.Token.TokenResponse;
import KBT2.comunity.back.entity.RefreshToken;
import KBT2.comunity.back.repository.RefreshTokenRepository;
import KBT2.comunity.back.service.TokenService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class TokenController {
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenService tokenService;

    @PostMapping("/token")
    public ResponseEntity<TokenResponse> refreshAccessToken(@RequestBody String refreshToken, HttpServletResponse response) {
        try {
            Optional<RefreshToken> storedToken = refreshTokenRepository.findByToken(refreshToken);
            RefreshToken token = storedToken.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 토큰입니다."));

            TokenDto tokenDto = tokenService.generateTokens(token.getUser());
            return ResponseEntity.ok(new TokenResponse(tokenDto.getAccessToken()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
