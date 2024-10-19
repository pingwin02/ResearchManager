package lab.jee.project.view;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lab.jee.component.ModelFunctionFactory;
import lab.jee.project.entity.Project;
import lab.jee.project.model.ProjectEditModel;
import lab.jee.project.service.ProjectService;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class ProjectEdit implements Serializable {

    private final ProjectService service;

    private final ModelFunctionFactory factory;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private ProjectEditModel project;

    @Inject
    public ProjectEdit(ProjectService projectService, ModelFunctionFactory factory) {
        this.service = projectService;
        this.factory = factory;
    }

    public void init() throws IOException {
        Optional<Project> project = service.find(id);
        if (project.isPresent()) {
            this.project = factory.projectToEditModel().apply(project.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Project not found");
        }
    }

    public String saveAction() {
        service.update(factory.updateProject().apply(service.find(id).orElseThrow(), project));
        return "project_view?faces-redirect=true&includeViewParams=true";
    }

}
