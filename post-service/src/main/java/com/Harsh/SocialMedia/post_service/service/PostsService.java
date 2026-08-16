package com.Harsh.SocialMedia.post_service.service;

import com.Harsh.SocialMedia.post_service.auth.UserContextHolder;
import com.Harsh.SocialMedia.post_service.clients.ConnectionClient;
import com.Harsh.SocialMedia.post_service.dto.PersonDto;
import com.Harsh.SocialMedia.post_service.dto.PostCreateRequestDto;
import com.Harsh.SocialMedia.post_service.dto.PostDto;
import com.Harsh.SocialMedia.post_service.entity.Post;
import com.Harsh.SocialMedia.post_service.events.PostCreatedEvent;
import com.Harsh.SocialMedia.post_service.exception.ResourceNotFoundException;
import com.Harsh.SocialMedia.post_service.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostsService {
    private final PostsRepository postsRepository;
    private final ModelMapper modelMapper;
    private final ConnectionClient connectionClient;
    private final KafkaTemplate<Long,PostCreatedEvent> kafkaTemplate;

    public PostDto createPost(PostCreateRequestDto postCreateRequestDto) {
        Long userId=UserContextHolder.getCurrentUserId();
        Post post=modelMapper.map(postCreateRequestDto,Post.class);
        post.setUserId(userId);
        Post savedPost=postsRepository.save(post);
        PostCreatedEvent postCreatedEvent=PostCreatedEvent.builder()
                .postId(savedPost.getId())
                .creatorId(userId)
                .content(savedPost.getContent())
                .build();
        kafkaTemplate.send("post-created-topic",postCreatedEvent);
        return modelMapper.map(savedPost, PostDto.class);
    }

    public Post getPostById(Long postId) {
        log.debug("Retrieving post with ID:{}",postId);
        return postsRepository.findById(postId).orElseThrow(()->
                new ResourceNotFoundException("Post not found with id:"+postId));
    }

    public List<PostDto> getAllPostsOfUser(Long userId) {
        List<Post> posts=postsRepository.findByUserId(userId);
        return posts.stream().map((element)->modelMapper.map(element,PostDto.class))
                .toList();
    }
}
