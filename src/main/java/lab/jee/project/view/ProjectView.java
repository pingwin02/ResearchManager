package lab.jee.project.view;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lab.jee.component.ModelFunctionFactory;
import lab.jee.experiment.model.ExperimentsModel;
import lab.jee.experiment.service.ExperimentService;
import lab.jee.project.entity.Project;
import lab.jee.project.model.ProjectModel;
import lab.jee.project.service.ProjectService;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class ProjectView implements Serializable {

    private final ProjectService projectService;

    private final ExperimentService experimentService;

    private final ModelFunctionFactory factory;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private ProjectModel project;

    @Getter
    private ExperimentsModel experiments;

    @Inject
    public ProjectView(ProjectService projectService, ExperimentService experimentService,
                       ModelFunctionFactory factory) {
        this.projectService = projectService;
        this.experimentService = experimentService;
        this.factory = factory;
    }

    public void init() throws IOException {
        Optional<Project> project = projectService.find(id);
        if (project.isPresent()) {
            this.project = factory.projectToModel().apply(project.get());
            this.experiments = factory.experimentsToModel().apply(experimentService.findAll().stream()
                    .filter(experiment -> experiment.getProject() != null && experiment.getProject().getId().equals(id))
                    .toList());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Project not found");
        }
    }
}
