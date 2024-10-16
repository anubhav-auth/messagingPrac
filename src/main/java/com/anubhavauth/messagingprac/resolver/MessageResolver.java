package com.anubhavauth.messagingprac.resolver;

import com.anubhavauth.messagingprac.entity.Message;
import com.anubhavauth.messagingprac.service.MessageService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.kickstart.tools.GraphQLSubscriptionResolver;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.util.List;

@Controller
public class MessageResolver {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    public MessageResolver(MessageService messageService, SimpMessagingTemplate messagingTemplate) {
        this.messageService = messageService;
        this.messagingTemplate = messagingTemplate;
    }

    @QueryMapping
    public List<Message> messages() {
        return messageService.getAllMessages();
    }

    @MutationMapping
    public Message sendMessage(@Argument String topic,@Argument String content, @Argument String sender) {
        Message newMessage = Message.builder().topic(topic).content(content).sender(sender).build();
        messageService.addMessage(topic, content, sender);
        messagingTemplate.convertAndSend("/topic/"+topic, newMessage);
        return newMessage;
    }

    @SubscriptionMapping //can add the name of the schema here like this if the method name is diff
    public Flux<Message> messageAdded(@Argument String topic) {
        return messageService.messageStream(topic);
    }
}
