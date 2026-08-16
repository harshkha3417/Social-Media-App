package com.Harsh.SocialMedia.post_service.events;


import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class PostCreatedEvent {
    Long creatorId;
    String content;
    Long postId;
}
