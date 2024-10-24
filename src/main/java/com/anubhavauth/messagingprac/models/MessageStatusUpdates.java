package com.anubhavauth.messagingprac.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageStatusUpdates {
    private String id;
    private String receiver;
    private MessageStatus status;
    private String deliveredAt;
    private String readAt;
}

