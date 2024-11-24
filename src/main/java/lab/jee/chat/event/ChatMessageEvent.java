package lab.jee.chat.event;

import lab.jee.chat.dto.Message;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ChatMessageEvent {

    private final Message message;

    private final String recipient;
}
