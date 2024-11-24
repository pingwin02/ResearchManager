package lab.jee.chat.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lab.jee.chat.service.ChatService;
import lab.jee.component.ModelFunctionFactory;
import lab.jee.researcher.model.ResearchersModel;
import lab.jee.researcher.service.ResearcherService;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Named
@RequestScoped
public class ChatView implements Serializable {

    private final ModelFunctionFactory factory;

    private final ChatService chatService;

    private final ResearcherService researcherService;

    private ResearchersModel researchers;

    @Getter
    @Setter
    private String messageContent;

    @Getter
    @Setter
    private String recipient;

    @Inject
    public ChatView(ModelFunctionFactory factory,
                    ChatService chatService,
                    ResearcherService researcherService) {
        this.factory = factory;
        this.chatService = chatService;
        this.researcherService = researcherService;
    }

    public ResearchersModel getResearchers() {
        if (researchers == null) {
            researchers = factory.researchersToModel().apply(researcherService.findAll());
        }
        return researchers;
    }

    public void sendAction() {
        if (recipient == null || recipient.isEmpty()) {
            chatService.sendMessageToAll(messageContent);
        } else {
            chatService.sendMessageToUser(recipient, messageContent);
        }
    }
}
