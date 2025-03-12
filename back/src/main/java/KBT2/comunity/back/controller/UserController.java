package KBT2.comunity.back.controller;

import KBT2.comunity.back.dto.Token.TokenDto;
import KBT2.comunity.back.dto.Token.TokenResponse;
import KBT2.comunity.back.dto.User.*;
import KBT2.comunity.back.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
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
    public ResponseEntity<Void> signUp(@Valid @RequestBody UserCreateRequest request) {
        userService.singUp(request);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody UserLoginRequest request, HttpServletResponse response) {
        TokenDto tokenDto = userService.login(request);

        ResponseCookie cookie = ResponseCookie.from("refreshToken", tokenDto.getRefreshToken())
                .httpOnly(true)
                .secure(false)
                .sameSite("Strict")
                .maxAge(60 * 60 * 24)
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
        return ResponseEntity.ok(new TokenResponse(tokenDto.getAccessToken()));
    }

    @GetMapping("")
    public ResponseEntity<UserDto> getUser(@AuthenticationPrincipal UUID id) {
        System.out.println(id);
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PatchMapping("")
    public ResponseEntity<UserDto> updateUser(@AuthenticationPrincipal UUID id, @Valid @RequestBody UserUpdateRequest request) {
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
