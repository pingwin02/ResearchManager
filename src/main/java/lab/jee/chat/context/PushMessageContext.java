package lab.jee.chat.context;

import jakarta.faces.push.Push;
import jakarta.faces.push.PushContext;
import jakarta.inject.Inject;
import lab.jee.chat.dto.Message;
import lombok.extern.java.Log;

import java.util.logging.Level;

@Log
public class PushMessageContext {

    private PushContext broadcastChannel;

    private PushContext userChannel;

    @Inject
    public PushMessageContext(
            @Push(channel = "broadcast") PushContext broadcastChannel,
            @Push(channel = "user") PushContext userChannel) {
        this.broadcastChannel = broadcastChannel;
        this.userChannel = userChannel;
    }

    public void notifyAll(Message message) {
        broadcastChannel.send(message);
    }

    public void notifyUser(Message message, String recipient) {
        try {
            userChannel.send(message, recipient);
        } catch (NullPointerException e) {
            log.log(Level.SEVERE, "Error sending message to user", e);
        }
    }
}
