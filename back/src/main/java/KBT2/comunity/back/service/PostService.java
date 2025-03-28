package KBT2.comunity.back.service;

import KBT2.comunity.back.dto.Post.PostCreateRequest;
import KBT2.comunity.back.dto.Post.PostDto;
import KBT2.comunity.back.dto.Response;
import KBT2.comunity.back.entity.Post;
import KBT2.comunity.back.entity.User;
import KBT2.comunity.back.exception.code.NotFoundException;
import KBT2.comunity.back.repository.PostRepository;
import KBT2.comunity.back.repository.UserRepository;
import KBT2.comunity.back.util.message.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ImageUploadService imageUploadService;

    public Response createPost(UUID userId, PostCreateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND));

        String imageUrl = null;
        MultipartFile logoImageFile = request.getImage();
        if (request.getImage() != null && !logoImageFile.isEmpty()) {
            try {
                imageUrl = imageUploadService.uploadToImgbb(logoImageFile);
            } catch (IOException e) {
                throw new RuntimeException("이미지 업로드 실패", e);
            }
        }

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .user(user)
                .image(imageUrl)
                .build();

        postRepository.save(post);
        user.getPosts().add(post);
        return new Response(post.getId());
    }

    public List<PostDto> getPostList() {
        return postRepository.findAllByOrderByUpdatedAtDesc().stream().map(PostDto::fromEntity).collect(Collectors.toList());
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
            String imageUrl = null;
            MultipartFile logoImageFile = request.getImage();
            if (!logoImageFile.isEmpty()) {
                try {
                    imageUrl = imageUploadService.uploadToImgbb(logoImageFile);
                } catch (IOException e) {
                    throw new RuntimeException("이미지 업로드 실패", e);
                }
            }
            post.setImage(imageUrl);
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
