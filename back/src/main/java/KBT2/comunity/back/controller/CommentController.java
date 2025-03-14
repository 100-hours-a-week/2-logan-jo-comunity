package KBT2.comunity.back.controller;

import KBT2.comunity.back.dto.Comment.CommentCreateRequest;
import KBT2.comunity.back.dto.Comment.CommentDto;
import KBT2.comunity.back.dto.Response;
import KBT2.comunity.back.service.CommentService;
import KBT2.comunity.back.util.ApiResponse;
import KBT2.comunity.back.util.message.SuccessMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/post/{postId}/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("")
    public ApiResponse<Response> createComment(@PathVariable UUID postId, @Valid @RequestBody CommentCreateRequest request) {
        return ApiResponse.ok(SuccessMessage.POST_SUCCESS, commentService.createComment(postId, request));
    }
    @GetMapping("")
    public ApiResponse<List<CommentDto>> getCommentList(@PathVariable UUID postId) {
        return ApiResponse.ok(SuccessMessage.FIND_SUCCESS, commentService.getCommentList(postId));
    }
    @GetMapping("/{commentId}")
    public ApiResponse<CommentDto> getComment(@PathVariable UUID commentId) {
        return ApiResponse.ok(SuccessMessage.FIND_SUCCESS, commentService.getComment(commentId));
    }
    @PatchMapping("/{commentId}")
    public ApiResponse<CommentDto> updateComment(@PathVariable UUID commentId, @Valid @RequestBody CommentCreateRequest request) {
        return ApiResponse.ok(SuccessMessage.PATCH_SUCCESS, commentService.updateComment(commentId, request));
    }
    @DeleteMapping("/{commentId}")
    public ApiResponse<Response> deleteComment(@PathVariable UUID commentId) {
        commentService.deleteComment(commentId);
        return ApiResponse.ok(SuccessMessage.DELETE_SUCCESS);
    }
}
