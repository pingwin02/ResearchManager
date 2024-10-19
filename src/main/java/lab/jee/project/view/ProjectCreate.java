package lab.jee.project.view;

import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lab.jee.component.ModelFunctionFactory;
import lab.jee.project.model.ProjectCreateModel;
import lab.jee.project.service.ProjectService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import java.io.Serializable;
import java.util.UUID;

@ConversationScoped
@Named
@Log
@NoArgsConstructor(force = true)
public class ProjectCreate implements Serializable {

    private final ProjectService projectService;

    private final ModelFunctionFactory factory;

    private final Conversation conversation;

    @Getter
    private ProjectCreateModel project;

    @Inject
    public ProjectCreate(ProjectService projectService, ModelFunctionFactory factory, Conversation conversation) {
        this.projectService = projectService;
        this.factory = factory;
        this.conversation = conversation;
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
        projectService.create(factory.modelToProject().apply(project));
        conversation.end();
        return "/project/project_list.xhtml?faces-redirect=true";
    }
}
