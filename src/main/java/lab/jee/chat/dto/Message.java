package lab.jee.chat.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public class Message {

    private String from;

    private String content;

}
