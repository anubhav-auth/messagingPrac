package com.anubhavauth.messagingprac.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    private String id;
    private String topic;
    private String content;
    private String sender;
    private String receiver;
    private MessageStatus status;
    private String sentAt;
    private String deliveredAt;
    private String readAt;
}

