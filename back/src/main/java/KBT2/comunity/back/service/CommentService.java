package KBT2.comunity.back.service;

import KBT2.comunity.back.dto.Comment.CommentCreateRequest;
import KBT2.comunity.back.dto.Comment.CommentDto;
import KBT2.comunity.back.dto.Response;
import KBT2.comunity.back.entity.Comment;
import KBT2.comunity.back.entity.Post;
import KBT2.comunity.back.entity.User;
import KBT2.comunity.back.exception.code.NotFoundException;
import KBT2.comunity.back.repository.CommentRepository;
import KBT2.comunity.back.repository.PostRepository;
import KBT2.comunity.back.util.message.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public Response createComment(UUID postId, CommentCreateRequest request) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException(ErrorMessage.POST_NOT_FOUND));
        User user = post.getUser();

        Comment comment = Comment.builder()
                .post(post)
                .content(request.getContent())
                .user(user)
                .build();

        commentRepository.save(comment);
        post.getComments().add(comment);
        user.getComments().add(comment);

        return new Response(comment.getId());
    }
    public List<CommentDto> getCommentList(UUID postId) {
        return commentRepository.findAllByPostId(postId)
                .stream()
                .map(CommentDto::fromEntity)
                .collect(Collectors.toList());
    }
    public CommentDto getComment(UUID commentId) {
        return CommentDto.fromEntity(commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.COMMENT_NOT_FOUND)));
    }
    @Transactional
    public CommentDto updateComment(UUID commentId, CommentCreateRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.COMMENT_NOT_FOUND));

        if (request.getContent() != null) {
            comment.setContent(request.getContent());
        }

        commentRepository.save(comment);
        return CommentDto.fromEntity(comment);
    }
    @Transactional
    public void deleteComment(UUID commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.COMMENT_NOT_FOUND));

        comment.setDeleted(true);
        commentRepository.save(comment);
    }
}
