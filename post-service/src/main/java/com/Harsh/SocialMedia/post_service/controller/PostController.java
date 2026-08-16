package com.Harsh.SocialMedia.post_service.controller;

import com.Harsh.SocialMedia.post_service.auth.UserContextHolder;
import com.Harsh.SocialMedia.post_service.dto.PostCreateRequestDto;
import com.Harsh.SocialMedia.post_service.dto.PostDto;
import com.Harsh.SocialMedia.post_service.entity.Post;
import com.Harsh.SocialMedia.post_service.service.PostsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class PostController {

    private final PostsService postsService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostCreateRequestDto postCreateRequestDto){
        PostDto createdPost=postsService.createPost(postCreateRequestDto);
        return ResponseEntity.ok(createdPost);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long postId){
        Post post=postsService.getPostById(postId);
        PostDto postDto=modelMapper.map(post,PostDto.class);
        return ResponseEntity.ok(postDto);
    }

    @GetMapping("/users/{userId}/allPosts")
    public ResponseEntity<List<PostDto>> getAllPostsOfUser(@PathVariable Long userId){
        List<PostDto> posts=postsService.getAllPostsOfUser(userId);
        return ResponseEntity.ok(posts);
    }
}
