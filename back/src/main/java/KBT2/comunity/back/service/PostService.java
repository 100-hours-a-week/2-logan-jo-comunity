package KBT2.comunity.back.service;

import KBT2.comunity.back.dto.Post.PostCreateRequest;
import KBT2.comunity.back.dto.Post.PostDto;
import KBT2.comunity.back.dto.Post.PostResponse;
import KBT2.comunity.back.entity.Post;
import KBT2.comunity.back.entity.User;
import KBT2.comunity.back.exception.code.NotFoundException;
import KBT2.comunity.back.util.message.ErrorMessage;
import KBT2.comunity.back.repository.PostRepositroy;
import KBT2.comunity.back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepositroy postRepository;
    private final UserRepository userRepository;

    public PostResponse createPost(UUID userId, PostCreateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND));

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .user(user)
                .image(request.getImage())
                .build();

        postRepository.save(post);
        user.getPosts().add(post);
        return new PostResponse(post.getId());
    }
    public List<PostDto> getPostList(UUID userId) {
        return postRepository.findAllByUserId(userId).stream().map(PostDto::fromEntity).collect(Collectors.toList());
    }
    @Transactional
    public PostDto getPost(UUID postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException(ErrorMessage.POST_NOT_FOUND));

        post.setViews(post.getViews() + 1);
        postRepository.save(post);

        return PostDto.fromEntity(post);
    }
    @Transactional
    public PostDto updatePost(UUID postId, PostCreateRequest request) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException(ErrorMessage.POST_NOT_FOUND));

        if (request.getTitle() != null) {
            post.setTitle(request.getTitle());
        }
        if (request.getContent() != null) {
            post.setContent(request.getContent());
        }
        if (request.getImage() != null) {
            post.setImage(request.getImage());
        }
        postRepository.save(post);

        return PostDto.fromEntity(post);
    }
    @Transactional
    public void addLike(UUID postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException(ErrorMessage.POST_NOT_FOUND));
        post.setLikes(post.getLikes() + 1);
        postRepository.save(post);
    }
    @Transactional
    public void deletePost(UUID postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException(ErrorMessage.POST_NOT_FOUND));

        post.setDeleted(true);
        postRepository.save(post);
    }
}
