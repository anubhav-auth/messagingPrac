package com.anubhavauth.messagingprac.service;

import com.anubhavauth.messagingprac.entity.Message;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MessageService {
    // In-memory list to store messages
    private final List<Message> messages = new ArrayList<>(Collections.emptyList());
    private final Sinks.Many<Message> messageSink = Sinks.many().multicast().directAllOrNothing();


    // Fetch all messages
    public List<Message> getAllMessages() {
        return messages;
    }

    public List<Message> addMessage(String topic,String content, String sender) {
        Message newMessage = new Message(String.valueOf(messages.size() + 1),topic, content, sender);
        messages.add(newMessage);

        // Emit the new message to all subscribers
        messageSink.tryEmitNext(newMessage);
        return messages;
    }

    // Method to return a stream of messages
    public Flux<Message> messageStream(String topic) {
        return messageSink.asFlux();
    }
}
