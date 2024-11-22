package lab.jee.project.view;

import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lab.jee.component.ModelFunctionFactory;
import lab.jee.project.model.ProjectsModel;
import lab.jee.project.service.ProjectService;

import java.io.Serializable;

@ViewScoped
@Named
public class ProjectList implements Serializable {

    private final ModelFunctionFactory factory;
    private ProjectService service;
    private ProjectsModel projects;

    @Inject
    public ProjectList(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setService(ProjectService service) {
        this.service = service;
    }

    public ProjectsModel getProjects() {
        if (projects == null) {
            projects = factory.projectsToModel().apply(service.findAll());
        }
        return projects;
    }

    public void deleteAction(ProjectsModel.Project project) {
        service.delete(project.getId());
        projects = null;
    }
}
