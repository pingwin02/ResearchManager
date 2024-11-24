package lab.jee.chat.observer;

import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import lab.jee.chat.context.PushMessageContext;
import lab.jee.chat.event.ChatMessageEvent;
import lab.jee.chat.qualifier.Broadcast;
import lab.jee.chat.qualifier.UserMessage;

public class ChatMessageObserver {

    @Inject
    private PushMessageContext pushMessageContext;

    public void onBroadcastMessage(@Observes @Broadcast ChatMessageEvent event) {
        pushMessageContext.notifyAll(event.getMessage());
    }

    public void onUserMessage(@Observes @UserMessage ChatMessageEvent event) {
        pushMessageContext.notifyUser(event.getMessage(), event.getRecipient());
    }
}
