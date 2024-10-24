package com.anubhavauth.messagingprac.service;

import com.anubhavauth.messagingprac.models.Message;
import com.anubhavauth.messagingprac.models.MessageStatusUpdates;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MessageService {
    private final Map<String, Sinks.Many<Message>> topicSinks = new ConcurrentHashMap<>();
    private final Map<String, Sinks.Many<MessageStatusUpdates>> statusUpdateSinks = new ConcurrentHashMap<>();


    public void addSink(Message mess) {
        topicSinks.computeIfAbsent(mess.getTopic(),t-> Sinks.many().multicast().directAllOrNothing()).tryEmitNext(mess);
    }

    // Method to return a stream of messages
    public Flux<Message> messageStream(String topic) {
        return topicSinks.computeIfAbsent(topic, t -> Sinks.many().multicast().directAllOrNothing())
                .asFlux();
    }
    public void addUpdateSink(MessageStatusUpdates mess) {
        statusUpdateSinks.computeIfAbsent(mess.getReceiver(),t-> Sinks.many().multicast().directAllOrNothing()).tryEmitNext(mess);
    }

    public Flux<MessageStatusUpdates> messageUpdateStream(String topic) {
        return statusUpdateSinks.computeIfAbsent(topic, t -> Sinks.many().multicast().directAllOrNothing())
                .asFlux();
    }
}
