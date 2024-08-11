package com.example.demo.ShiftRaport;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendNotification(String message) {
        messagingTemplate.convertAndSend("/topic/notifications", message);
        System.out.println(messagingTemplate.getUserDestinationPrefix());
        System.out.println(messagingTemplate.getHeaderInitializer());
    }
}
