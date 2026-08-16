package com.Harsh.SocialMedia.post_service.service;

import com.Harsh.SocialMedia.post_service.auth.UserContextHolder;
import com.Harsh.SocialMedia.post_service.entity.Post;
import com.Harsh.SocialMedia.post_service.entity.PostLike;
import com.Harsh.SocialMedia.post_service.events.PostLikedEvent;
import com.Harsh.SocialMedia.post_service.exception.ResourceNotFoundException;
import com.Harsh.SocialMedia.post_service.repository.PostLikeRepository;
import com.Harsh.SocialMedia.post_service.repository.PostsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostsRepository postsRepository;
    private final KafkaTemplate<Long,PostLikedEvent> kafkaTemplate;

    public void likePost(Long postId){
        Long userId=UserContextHolder.getCurrentUserId();
        log.info("Attempting to like the post with id:{}",postId);
        Post post=postsRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("Post not found with id:"+postId)
        );

        boolean alreadyLiked= postLikeRepository.existsByUserIdAndPostId(userId,postId);
        if(alreadyLiked){
            throw new RuntimeException("post is already liked");
        }else{
            PostLike postLike=new PostLike();
            postLike.setPostId(postId);
            postLike.setUserId(userId);
            postLikeRepository.save(postLike);
            log.info("post wit id:{} liked successfully",postId);
            PostLikedEvent postLikedEvent=PostLikedEvent.builder()
                    .postId(postId)
                    .likedByUserId(userId)
                    .creatorId(post.getUserId())
                    .build();
            kafkaTemplate.send("post-liked-topic",postId,postLikedEvent);
        }

    }

    public void unlikePost(Long postId){
        Long userId= UserContextHolder.getCurrentUserId();
        log.info("Attempting to unlike the post with id:{}",postId);
        boolean exists=postsRepository.existsById(postId);
        if(!exists) throw new ResourceNotFoundException("Post not found with id:"+ postId);
        boolean alreadyLiked= postLikeRepository.existsByUserIdAndPostId(userId,postId);
        if(!alreadyLiked){
            throw new RuntimeException("post is already liked");
        }else{
            postLikeRepository.deleteByUserIdAndPostId(userId,postId);
            log.info("post with id:{} unliked successfully",postId);
        }

    }
}
