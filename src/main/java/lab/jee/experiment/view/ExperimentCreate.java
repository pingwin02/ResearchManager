package lab.jee.experiment.view;

import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lab.jee.component.ModelFunctionFactory;
import lab.jee.experiment.model.ExperimentCreateModel;
import lab.jee.experiment.service.ExperimentService;
import lab.jee.project.model.ProjectModel;
import lab.jee.project.service.ProjectService;
import lab.jee.researcher.service.ResearcherService;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@ConversationScoped
@Named
@NoArgsConstructor(force = true)
public class ExperimentCreate implements Serializable {

    private final ExperimentService experimentService;

    private final ProjectService projectService;

    private final ResearcherService researcherService;

    private final ModelFunctionFactory factory;

    private final Conversation conversation;

    @Getter
    private ExperimentCreateModel experiment;

    @Getter
    private List<ProjectModel> projects;

    @Inject
    public ExperimentCreate(ExperimentService experimentService,
                            ProjectService projectService,
                            ResearcherService researcherService,
                            ModelFunctionFactory factory,
                            Conversation conversation) {
        this.experimentService = experimentService;
        this.projectService = projectService;
        this.researcherService = researcherService;
        this.factory = factory;
        this.conversation = conversation;
    }

    public void init() {
        if (conversation.isTransient()) {
            projects = projectService.findAll().stream()
                    .map(factory.projectToModel()::apply)
                    .toList();
            experiment = ExperimentCreateModel.builder()
                    .id(UUID.randomUUID())
                    // TODO
                    .researcher(researcherService.find("clayre").get().getId())
                    .build();
            conversation.begin();
        }
    }

    public String goToProjectAction() {
        return "/experiment/experiment_create__project.xhtml?faces-redirect=true";
    }

    public String goToBasicAction() {
        return "/experiment/experiment_create__basic.xhtml?faces-redirect=true";
    }

    public String cancelAction() {
        conversation.end();
        return "/experiment/experiment_list.xhtml?faces-redirect=true";
    }

    public String goToConfirmAction() {
        return "/experiment/experiment_create__confirm.xhtml?faces-redirect=true";
    }

    public String saveAction() {
        experimentService.create(factory.modelToExperiment().apply(experiment));
        conversation.end();
        return "/experiment/experiment_list.xhtml?faces-redirect=true";
    }
}
