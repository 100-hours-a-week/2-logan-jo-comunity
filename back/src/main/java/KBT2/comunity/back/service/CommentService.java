package KBT2.comunity.back.service;

import KBT2.comunity.back.dto.Comment.CommentCreateRequest;
import KBT2.comunity.back.dto.Comment.CommentDto;
import KBT2.comunity.back.dto.Response;
import KBT2.comunity.back.entity.Comment;
import KBT2.comunity.back.entity.Post;
import KBT2.comunity.back.entity.User;
import KBT2.comunity.back.exception.code.NotFoundException;
import KBT2.comunity.back.repository.CommentRepositoryJdbc;
import KBT2.comunity.back.repository.PostRepository;
import KBT2.comunity.back.util.message.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    //    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentRepositoryJdbc commentRepositoryJdbc;

    @Transactional
    public Response createComment(UUID postId, CommentCreateRequest request) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException(ErrorMessage.POST_NOT_FOUND));
        User user = post.getUser();

        UUID commentId = UUID.randomUUID();

        Comment comment = Comment.builder()
                .id(commentId)
                .postId(post.getId())
                .content(request.getContent())
                .userId(user.getId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .isDeleted(false)
                .build();

//        commentRepository.save(comment);
        commentRepositoryJdbc.save(comment);
//        post.getComments().add(comment);
//        user.getComments().add(comment);
        post.setComments(post.getComments() + 1);

        return new Response(comment.getId());
    }

    public List<CommentDto> getCommentList(UUID postId) {
//        return commentRepository.findAllByPostId(postId)
//                .stream()
//                .map(CommentDto::fromEntity)
//                .collect(Collectors.toList());
        return commentRepositoryJdbc.findAllByPostId(postId)
                .stream()
                .map(CommentDto::fromEntity)
                .collect(Collectors.toList());
    }

    public CommentDto getComment(UUID commentId) {
//        return CommentDto.fromEntity(commentRepository.findById(commentId)
//                .orElseThrow(() -> new NotFoundException(ErrorMessage.COMMENT_NOT_FOUND)));
        return CommentDto.fromEntity(commentRepositoryJdbc.findById(commentId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.COMMENT_NOT_FOUND)));
    }

    @Transactional
    public CommentDto updateComment(UUID commentId, CommentCreateRequest request) {
//        Comment comment = commentRepository.findById(commentId)
//                .orElseThrow(() -> new NotFoundException(ErrorMessage.COMMENT_NOT_FOUND));
        Comment comment = commentRepositoryJdbc.findById(commentId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.COMMENT_NOT_FOUND));

        if (request.getContent() != null) {
            comment.setContent(request.getContent());
        }

//        commentRepository.save(comment);
        commentRepositoryJdbc.update(comment);
        return CommentDto.fromEntity(comment);
    }

    @Transactional
    public void deleteComment(UUID commentId) {
//        Comment comment = commentRepository.findById(commentId)
//                .orElseThrow(() -> new NotFoundException(ErrorMessage.COMMENT_NOT_FOUND));

//        comment.setDeleted(true);
//        commentRepository.save(comment);
        commentRepositoryJdbc.softDelete(commentId);
    }
}
