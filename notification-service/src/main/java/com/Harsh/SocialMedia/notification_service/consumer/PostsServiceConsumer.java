package com.Harsh.SocialMedia.notification_service.consumer;

import com.Harsh.SocialMedia.post_service.events.PostCreatedEvent;
import com.Harsh.SocialMedia.post_service.events.PostLikedEvent;
import com.Harsh.SocialMedia.notification_service.clients.ConnectionClient;
import com.Harsh.SocialMedia.notification_service.dto.PersonDto;
import com.Harsh.SocialMedia.notification_service.repository.NotificationRepository;
import com.Harsh.SocialMedia.notification_service.service.SendNotification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostsServiceConsumer {
    private final ConnectionClient connectionClient;
    private final NotificationRepository notificationRepository;
    private final SendNotification sendNotification;
    @KafkaListener(topics="post-created-topic")
    public void handlePostCreated(PostCreatedEvent postCreatedEvent){
        log.info("Sending notifications: handlePostCreated:{}",postCreatedEvent);
        List<PersonDto> firstconnections=connectionClient.getFirstConnection(postCreatedEvent.getCreatorId());
        List<PersonDto> secondconnections=connectionClient.getSecondConnection(postCreatedEvent.getCreatorId());
        List<PersonDto> thirdconnections=connectionClient.getThirdConnection(postCreatedEvent.getCreatorId());

        for(PersonDto connection:firstconnections){
            sendNotification.send(connection.getUserId(),"Your connection"+postCreatedEvent.getCreatorId()+" a post,Check it out");
        }
        for(PersonDto connection:secondconnections){
            sendNotification.send(connection.getUserId(),"Your Second connection"+postCreatedEvent.getCreatorId()+" a post,Check it out");
        }
        for(PersonDto connection:thirdconnections){
            sendNotification.send(connection.getUserId(),"Your Third connection"+postCreatedEvent.getCreatorId()+" a post,Check it out");
        }
    }

    @KafkaListener(topics = "post-liked-topic")
    public void handlePostLiked(PostLikedEvent postLikedEvent){
        log.info("Sending notifications: handlePostLiked:{}",postLikedEvent);
        String message=String.format("Your post,%d has been liked by %d",postLikedEvent.getPostId(),postLikedEvent.getLikedByUserId());
        sendNotification.send(postLikedEvent.getCreatorId(),message);

    }

}
