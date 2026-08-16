package com.Harsh.SocialMedia.notification_service.consumer;

import com.Harsh.SocialMedia.connection_service.events.AcceptConnectionRequestEvent;
import com.Harsh.SocialMedia.connection_service.events.SendConnectionRequestEvent;
import com.Harsh.SocialMedia.notification_service.service.SendNotification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionServiceConsumer {
    private final SendNotification sendNotification;

    @KafkaListener(topics="send-connection-request-topic")
    public void handleSendConnectionRequest(SendConnectionRequestEvent sendConnectionRequestEvent){
        String message="You have received a connection request from user with id: %d"+sendConnectionRequestEvent.getSenderId();
        sendNotification.send(sendConnectionRequestEvent.getReceiverId(),message);
    }
    @KafkaListener(topics="accept-connection-request-topic")
    public void handleAcceptConnectionRequest(AcceptConnectionRequestEvent acceptConnectionRequestEvent){
        String message="Your connection request has been accepted by user with id: %d"+acceptConnectionRequestEvent.getReceiverId();
        sendNotification.send(acceptConnectionRequestEvent.getSenderId(),message);
    }
}
