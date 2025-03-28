package com.cryptory.be.post.service;

import com.cryptory.be.coin.domain.Coin;
import com.cryptory.be.coin.exception.CoinErrorCode;
import com.cryptory.be.coin.exception.CoinException;
import com.cryptory.be.coin.repository.CoinRepository;
import com.cryptory.be.global.util.DateFormat;
import com.cryptory.be.global.util.FileUtils;
import com.cryptory.be.post.domain.Post;
import com.cryptory.be.post.domain.PostFile;
import com.cryptory.be.post.dto.*;
import com.cryptory.be.post.exception.PostErrorCode;
import com.cryptory.be.post.exception.PostException;
import com.cryptory.be.post.repository.PostFileRepository;
import com.cryptory.be.post.repository.PostRepository;
import com.cryptory.be.user.domain.User;
import com.cryptory.be.user.exception.UserErrorCode;
import com.cryptory.be.user.exception.UserException;
import com.cryptory.be.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CoinRepository coinRepository;
    private final PostFileRepository postFileRepository;
    private final FileUtils fileUtils;


    public PostListDto getPosts(Long coinId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postsPage = postRepository.findAllByCoinId(coinId, pageable);

        List<PostDto> posts = postsPage.stream()
                .filter(Post::isNotDeleted)
                .map(post -> new PostDto(
                        post.getId(),
                        post.getTitle(),
                        post.getUser().getNickname(),
                        DateFormat.formatDate(post.getCreatedAt())
                )).toList();

        long totalItems = getTotalItems(coinId);
        int totalPages = getTotalPages(coinId, size);

        return new PostListDto(posts, totalItems, totalPages);
    }


    @Transactional
    public PostDto createPost(Long coinId, String userId, CreatePostDto createPostDto, List<MultipartFile> files) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.NOT_EXIST_USER));

        Coin coin = coinRepository.findById(coinId)
                .orElseThrow(() -> new CoinException(CoinErrorCode.COIN_DATA_MISSING));

        Post post = postRepository.save(Post.builder()
                .title(createPostDto.getTitle())
                .body(createPostDto.getBody())
                .user(user)
                .coin(coin)
                .build());

        // 파일이 존재하는 경우
        if (files != null) {
            for (MultipartFile file : files) {
                String storedDir = fileUtils.saveFile(file);

                String fileType = storedDir.substring(storedDir.lastIndexOf(".") + 1);

                PostFile postFile = PostFile.builder()
                        .originalFilename(file.getOriginalFilename())
                        .storedDir(storedDir)
                        .storedFilename(fileUtils.getStoredFileName(storedDir))
                        .post(post)
                        .fileType(fileType)
                        .build();

                postFileRepository.save(postFile);
            }
        }

        return new PostDto(
                post.getId(),
                post.getTitle(),
                post.getUser().getNickname(),
                DateFormat.formatDate(post.getCreatedAt())
        );
    }

    @Transactional
    public void deletePost(Long coinId, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(PostErrorCode.NOT_EXIST_POST));

        post.delete();
    }


    @Transactional
    public PostDetailDto getPost(Long coinId, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(PostErrorCode.NOT_EXIST_POST));

        post.increaseViewCnt();

        List<PostFileDto> postFiles = postFileRepository.findAllByPostId(postId).stream()
                .map(postFile -> new PostFileDto(
                        postFile.getId(),
                        postFile.getOriginalFilename(),
                        postFile.getStoredDir(),
                        DateFormat.formatDate(postFile.getCreatedAt())
                )).toList();

        return new PostDetailDto(post.getTitle(), post.getBody(), post.getUser().getNickname(), DateFormat.formatDate(post.getCreatedAt()), postFiles, post.getViewCnt(), post.getLikeCnt());
    }

    @Transactional
    public void updatePost(Long coinId, Long postId, UpdatePostDto updatePostDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(PostErrorCode.NOT_EXIST_POST));

        post.update(updatePostDto.getTitle(), updatePostDto.getBody());

        // todo: 파일 업데이트(클라이언트 요청에 따라)
    }

    private long getTotalItems(Long coinId) {
        return postRepository.countByCoinId(coinId);  // 게시글 개수 반환
    }

    private int getTotalPages(Long coinId, int size) {
        long totalItems = getTotalItems(coinId);  // 전체 아이템 개수
        return (int) Math.ceil((double) totalItems / size);  // 전체 아이템 수로 총 페이지 수 계산
    }

}
