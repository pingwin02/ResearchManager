package lab.jee.experiment.view;

import jakarta.ejb.EJB;
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

    private final ModelFunctionFactory factory;
    private final Conversation conversation;
    private ExperimentService experimentService;
    private ProjectService projectService;
    private ResearcherService researcherService;
    @Getter
    private ExperimentCreateModel experiment;

    @Getter
    private List<ProjectModel> projects;

    @Inject
    public ExperimentCreate(ModelFunctionFactory factory,
                            Conversation conversation) {
        this.factory = factory;
        this.conversation = conversation;
    }

    @EJB
    public void setExperimentService(ExperimentService experimentService) {
        this.experimentService = experimentService;
    }

    @EJB
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    @EJB
    public void setResearcherService(ResearcherService researcherService) {
        this.researcherService = researcherService;
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
