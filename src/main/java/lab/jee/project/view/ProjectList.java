package lab.jee.project.view;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lab.jee.component.ModelFunctionFactory;
import lab.jee.project.model.ProjectsModel;
import lab.jee.project.service.ProjectService;

@RequestScoped
@Named
public class ProjectList {

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

    public String deleteAction(ProjectsModel.Project project) {
        service.delete(project.getId());
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }
}
