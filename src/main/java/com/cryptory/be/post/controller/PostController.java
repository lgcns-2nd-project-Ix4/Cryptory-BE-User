package com.cryptory.be.post.controller;

import com.cryptory.be.global.response.ApiResponse;
import com.cryptory.be.post.dto.*;
import com.cryptory.be.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/coins/{coinId}/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public ApiResponse<PostListDto> getPosts(@PathVariable("coinId") Long coinId,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size) {
        PostListDto posts = postService.getPosts(coinId, page, size);

        return new ApiResponse<>(HttpStatus.OK, posts);
    }

    @GetMapping("/{postId}")
    public ApiResponse<PostDetailDto> getPost(@PathVariable("coinId") Long coinId,
                                                 @PathVariable("postId") Long postId) {
        PostDetailDto post = postService.getPost(coinId, postId);
        return new ApiResponse<>(HttpStatus.OK, post);
    }

    @PostMapping
    public ApiResponse<PostDto> createPost(Principal principal,
                                              @PathVariable("coinId") Long coinId,
                                              @RequestPart(value = "post") CreatePostDto createPostDto,
                                              @RequestPart(required = false) List<MultipartFile> files) {
        PostDto post = postService.createPost(coinId, principal.getName(), createPostDto, files);
        return new ApiResponse<>(HttpStatus.CREATED, post);
    }

    @PutMapping("/{postId}")
    public ApiResponse<?> deletePost(@PathVariable("coinId") Long coinId,
                                        @PathVariable("postId") Long postId) {
        postService.deletePost(coinId, postId);

        return new ApiResponse<>(HttpStatus.OK, "게시글을 삭제했습니다.");
    }

    @PatchMapping("/{postId}")
    public ApiResponse<?> updatePost(@PathVariable("coinId") Long coinId,
                                              @PathVariable("postId") Long postId,
                                              @RequestPart(value = "post", required = false) UpdatePostDto updatePostDto) {
        postService.updatePost(coinId, postId, updatePostDto);
        return new ApiResponse<>(HttpStatus.OK, "게시글을 수정했습니다.");
    }

    @GetMapping("/{postId}/comments")
    public ApiResponse<?> getComments(@PathVariable("coinId") Long coinId,
                                      @PathVariable("postId") Long postId) {

        return new ApiResponse<>(HttpStatus.OK, "등록된 댓글이 없습니다.");
    }
}
