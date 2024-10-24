package com.anubhavauth.messagingprac.resolver;

import com.anubhavauth.messagingprac.models.Message;
import com.anubhavauth.messagingprac.models.MessageStatus;
import com.anubhavauth.messagingprac.models.MessageStatusUpdates;
import com.anubhavauth.messagingprac.service.MessageService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

@Controller
public class MessageResolver {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    public MessageResolver(MessageService messageService, SimpMessagingTemplate messagingTemplate) {
        this.messageService = messageService;
        this.messagingTemplate = messagingTemplate;
    }

    @MutationMapping
    public Message sendMessage(@Argument String id, @Argument String topic, @Argument String content, @Argument String sender, @Argument String sentAt) {
        Message newMessage = Message.builder()
                .id(id)
                .topic(topic)
                .content(content)
                .sender(sender)
                .receiver(topic)
                .status(MessageStatus.SENT)
                .sentAt(sentAt)
                .deliveredAt("")
                .readAt("")
                .build();
        messageService.addSink(newMessage);
        messagingTemplate.convertAndSend("/topic/" + topic, newMessage);
        return newMessage;
    }


    @MutationMapping
    public MessageStatusUpdates statusUpdate(@Argument String messageId, @Argument String topic, @Argument MessageStatus status, @Argument String deliveredAt, @Argument String readAt) {
        MessageStatusUpdates messageStatusUpdates = MessageStatusUpdates.builder()
                .id(messageId)
                .receiver(topic)
                .status(status)
                .deliveredAt(deliveredAt)
                .readAt(readAt)
                .build();
        messageService.addUpdateSink(messageStatusUpdates);
        messagingTemplate.convertAndSend("/topic/" + topic, messageStatusUpdates);
        return messageStatusUpdates;
    }

    @SubscriptionMapping //can add the name of the schema here like this if the method name is diff @SubscriptionMapping(...)
    public Flux<Message> messageAdded(@Argument String topic) {
        return messageService.messageStream(topic);
    }

    @SubscriptionMapping
    public Flux<MessageStatusUpdates> messageUpdates(@Argument String topic) {
        return messageService.messageUpdateStream(topic);
    }

}
