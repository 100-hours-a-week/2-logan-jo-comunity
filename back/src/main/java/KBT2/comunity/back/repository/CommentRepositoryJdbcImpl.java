package KBT2.comunity.back.repository;

import KBT2.comunity.back.entity.Comment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CommentRepositoryJdbcImpl implements CommentRepositoryJdbc {
    private final JdbcTemplate jdbcTemplate;

    public CommentRepositoryJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Comment> commentRowMapper = (rs, rowNum) -> Comment.builder()
            .id(fromBytes(rs.getBytes("id")))
            .postId(fromBytes(rs.getBytes("post_id")))
            .userId(fromBytes(rs.getBytes("user_id")))
            .content(rs.getString("content"))
            .isDeleted(rs.getBoolean("is_deleted"))
            .createdAt(rs.getObject("created_at", LocalDateTime.class))
            .updatedAt(rs.getObject("updated_at", LocalDateTime.class))
            .build();

    @Override
    public List<Comment> findAllByPostId(UUID postId) {
        String sql = "SELECT * FROM comment WHERE post_id = ? AND is_deleted = false";
        return jdbcTemplate.query(sql, commentRowMapper, toBytes(postId));
    }

    @Override
    public Optional<Comment> findById(UUID id) {
        String sql = "SELECT * FROM comment WHERE id = ? AND is_deleted = false";
        return jdbcTemplate.query(sql, commentRowMapper, toBytes(id)).stream().findFirst();
    }

    @Override
    public void save(Comment comment) {
        String sql = "INSERT INTO comment (id, post_id, user_id, content, is_deleted, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                toBytes(comment.getId()),
                toBytes(comment.getPostId()),
                toBytes(comment.getUserId()),
                comment.getContent(),
                false,
                comment.getCreatedAt(),
                comment.getUpdatedAt());
    }

    @Override
    public void update(Comment comment) {
        String sql = "UPDATE comment SET content = ?, updated_at = ? WHERE id = ?";
        jdbcTemplate.update(sql, comment.getContent(), comment.getUpdatedAt(), toBytes(comment.getId()));
    }

    @Override
    public void softDelete(UUID id) {
        String sql = "UPDATE comment SET is_deleted = true WHERE id = ?";
        jdbcTemplate.update(sql, toBytes(id));
    }

    private byte[] toBytes(UUID uuid) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
        byteBuffer.putLong(uuid.getMostSignificantBits());
        byteBuffer.putLong(uuid.getLeastSignificantBits());
        return byteBuffer.array();
    }

    private UUID fromBytes(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        long mostSigBits = byteBuffer.getLong();
        long leastSigBits = byteBuffer.getLong();
        return new UUID(mostSigBits, leastSigBits);
    }
}
