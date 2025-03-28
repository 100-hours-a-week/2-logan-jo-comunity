package KBT2.comunity.back.controller;

import KBT2.comunity.back.dto.Token.TokenDto;
import KBT2.comunity.back.dto.Token.TokenResponse;
import KBT2.comunity.back.dto.User.*;
import KBT2.comunity.back.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@Valid @ModelAttribute UserCreateRequest request) {
        userService.singUp(request);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody UserLoginRequest request, HttpServletResponse response) {
        TokenDto tokenDto = userService.login(request);

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", tokenDto.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .maxAge(60 * 60 * 24)
                .path("/")
                .build();

        response.setHeader("Set-Cookie", refreshTokenCookie.toString());
        return ResponseEntity.ok(new TokenResponse(tokenDto.getAccessToken()));
    }

    @GetMapping("")
    public ResponseEntity<UserDto> getUser(@AuthenticationPrincipal UUID id) {
        System.out.println(id);
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PatchMapping("")
    public ResponseEntity<UserDto> updateUser(@AuthenticationPrincipal UUID id, @Valid @ModelAttribute UserUpdateRequest request) {
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword(@AuthenticationPrincipal UUID id, @Valid @RequestBody UserPasswordUpdateRequest request) {
        userService.updatePassword(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("")
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
