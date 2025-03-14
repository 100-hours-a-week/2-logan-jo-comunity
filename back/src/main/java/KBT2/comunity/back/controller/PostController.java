package KBT2.comunity.back.controller;

import KBT2.comunity.back.dto.Post.PostCreateRequest;
import KBT2.comunity.back.dto.Post.PostDto;
import KBT2.comunity.back.dto.Response;
import KBT2.comunity.back.service.PostService;
import KBT2.comunity.back.util.ApiResponse;
import KBT2.comunity.back.util.message.SuccessMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("")
    public ApiResponse<Response> createPost(@AuthenticationPrincipal UUID userId, @Valid @RequestBody PostCreateRequest request) {
        return ApiResponse.ok(SuccessMessage.POST_SUCCESS, postService.createPost(userId, request));
    }
    @GetMapping("")
    public ApiResponse<List<PostDto>> getPostList(@AuthenticationPrincipal UUID userId) {
        return ApiResponse.ok(SuccessMessage.FIND_SUCCESS, postService.getPostList(userId));
    }
    @GetMapping("/{postId}")
    public ApiResponse<PostDto> getPost(@PathVariable UUID postId) {
        return ApiResponse.ok(postService.getPost(postId));
    }
    @PatchMapping("/{postId}")
    public ApiResponse<PostDto> updatePost(@PathVariable UUID postId, @Valid @RequestBody PostCreateRequest request) {
        return ApiResponse.ok(SuccessMessage.PATCH_SUCCESS, postService.updatePost(postId, request));
    }
    @PatchMapping("/{postId}/like")
    public ApiResponse<Void> addLike(@PathVariable UUID postId) {
        postService.addLike(postId);
        return ApiResponse.ok(SuccessMessage.PATCH_SUCCESS);
    }
    @DeleteMapping("/{postId}")
    public ApiResponse<Void> deletePost(@PathVariable UUID postId) {
        postService.deletePost(postId);
        return ApiResponse.ok(SuccessMessage.DELETE_SUCCESS);
    }
}
