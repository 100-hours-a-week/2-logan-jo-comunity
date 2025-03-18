package KBT2.comunity.back.repository;

import KBT2.comunity.back.entity.Comment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentRepositoryJdbc {
    List<Comment> findAllByPostId(UUID postId);

    Optional<Comment> findById(UUID id);

    void save(Comment comment);

    void update(Comment comment);

    void softDelete(UUID id);
}
