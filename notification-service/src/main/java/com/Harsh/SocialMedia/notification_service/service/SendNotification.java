package com.Harsh.SocialMedia.notification_service.service;

import com.Harsh.SocialMedia.notification_service.entity.Notification;
import com.Harsh.SocialMedia.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendNotification {
    private final NotificationRepository notificationRepository;
    public void send(Long userId,String message){
        Notification notification=new Notification();
        notification.setMessage(message);
        notification.setUserId(userId);
        notificationRepository.save(notification);
    }
}
