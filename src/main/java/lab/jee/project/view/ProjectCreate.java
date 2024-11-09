package lab.jee.project.view;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lab.jee.component.ModelFunctionFactory;
import lab.jee.project.model.ProjectCreateModel;
import lab.jee.project.service.ProjectService;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@ConversationScoped
@Named
@NoArgsConstructor(force = true)
public class ProjectCreate implements Serializable {

    private final ModelFunctionFactory factory;
    private final Conversation conversation;
    private ProjectService service;
    @Getter
    private ProjectCreateModel project;

    @Inject
    public ProjectCreate(ModelFunctionFactory factory, Conversation conversation) {
        this.factory = factory;
        this.conversation = conversation;
    }

    @EJB
    public void setService(ProjectService service) {
        this.service = service;
    }

    public void init() {
        if (conversation.isTransient()) {
            project = ProjectCreateModel.builder()
                    .id(UUID.randomUUID())
                    .build();
            conversation.begin();
        }
    }

    public String goToBasicAction() {
        return "/project/project_create__basic.xhtml?faces-redirect=true";
    }

    public String cancelAction() {
        conversation.end();
        return "/project/project_list.xhtml?faces-redirect=true";
    }

    public String goToConfirmAction() {
        return "/project/project_create__confirm.xhtml?faces-redirect=true";
    }

    public String saveAction() {
        service.create(factory.modelToProject().apply(project));
        conversation.end();
        return "/project/project_list.xhtml?faces-redirect=true";
    }
}
