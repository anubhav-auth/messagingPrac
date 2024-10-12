package com.anubhavauth.messagingprac.entity;

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
}
