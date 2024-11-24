package lab.jee.chat.service;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import lab.jee.chat.dto.Message;
import lab.jee.chat.event.ChatMessageEvent;
import lab.jee.chat.qualifier.Broadcast;
import lab.jee.chat.qualifier.UserMessage;
import lombok.NoArgsConstructor;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class ChatService {

    private final SecurityContext securityContext;

    private final Event<ChatMessageEvent> broadcastEvent;

    private final Event<ChatMessageEvent> userMessageEvent;

    @Inject
    public ChatService(SecurityContext securityContext,
                       @Broadcast Event<ChatMessageEvent> broadcastEvent,
                       @UserMessage Event<ChatMessageEvent> userMessageEvent) {
        this.securityContext = securityContext;
        this.broadcastEvent = broadcastEvent;
        this.userMessageEvent = userMessageEvent;
    }

    public void sendMessageToAll(String content) {
        String from = securityContext.getCallerPrincipal().getName();
        Message message = Message.builder().from(from).content(content).build();
        broadcastEvent.fire(new ChatMessageEvent(message, null));
    }

    public void sendMessageToUser(String to, String content) {
        String from = securityContext.getCallerPrincipal().getName();
        Message message = Message.builder().from(from).content(content).build();
        userMessageEvent.fire(new ChatMessageEvent(message, to));

        if (!to.equals(from)) {
            Message messageForSender = Message.builder().from(to + " -->").content(content).build();
            userMessageEvent.fire(new ChatMessageEvent(messageForSender, from));
        }
    }
}
