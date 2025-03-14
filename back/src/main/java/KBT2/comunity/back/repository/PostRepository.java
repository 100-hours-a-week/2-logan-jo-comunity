package KBT2.comunity.back.repository;

import KBT2.comunity.back.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    List<Post> findAllByUserId(UUID userId);
}
