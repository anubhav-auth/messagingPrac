package com.anubhavauth.messagingprac.service;

import com.anubhavauth.messagingprac.entity.Message;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MessageService {
    // In-memory list to store messages
    private final List<Message> messages = new ArrayList<>(Collections.emptyList());
    private final Map<String, Sinks.Many<Message>> topicSinks = new ConcurrentHashMap<>();


    // Fetch all messages
    public List<Message> getAllMessages() {
        return messages;
    }

    public void addMessage(String topic, String content, String sender) {
        Message newMessage = new Message(String.valueOf(messages.size() + 1),topic, content, sender);
        messages.add(newMessage);

        // Emit the new message to all subscribers
        topicSinks.computeIfAbsent(topic,t-> Sinks.many().multicast().directAllOrNothing()).tryEmitNext(newMessage);
    }

    // Method to return a stream of messages
    public Flux<Message> messageStream(String topic) {

        return topicSinks.computeIfAbsent(topic, t -> Sinks.many().multicast().directAllOrNothing())
                .asFlux();
    }
}
