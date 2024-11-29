package lab.jee.experiment.view;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.OptimisticLockException;
import jakarta.servlet.http.HttpServletResponse;
import lab.jee.component.ModelFunctionFactory;
import lab.jee.experiment.entity.Experiment;
import lab.jee.experiment.model.ExperimentEditModel;
import lab.jee.experiment.service.ExperimentService;
import lab.jee.project.model.ProjectModel;
import lab.jee.project.service.ProjectService;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class ExperimentEdit implements Serializable {

    private final ModelFunctionFactory factory;
    private final FacesContext facesContext;
    @Getter
    private final ExperimentEditModel experiment;
    private ExperimentService experimentService;
    private ProjectService projectService;
    @Setter
    @Getter
    private UUID id;
    @Getter
    private List<ProjectModel> projects;

    @Inject
    public ExperimentEdit(ModelFunctionFactory factory,
                          FacesContext facesContext,
                          ExperimentEditModel experiment) {
        this.factory = factory;
        this.facesContext = facesContext;
        this.experiment = experiment;
    }

    @EJB
    public void setExperimentService(ExperimentService experimentService) {
        this.experimentService = experimentService;
    }

    @EJB
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    public void init() throws IOException {
        projects = projectService.findAll().stream()
                .map(factory.projectToModel()::apply)
                .toList();
        Optional<Experiment> experiment = experimentService.findForCallerPrincipal(id);
        if (experiment.isPresent()) {
            this.experiment.setDescription(experiment.get().getDescription());
            this.experiment.setSuccess(experiment.get().isSuccess());
            this.experiment.setDateConducted(experiment.get().getDateConducted());
            this.experiment.setVersion(experiment.get().getVersion());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Experiment not found");
        }
    }

    public String saveAction() throws IOException {
        try {
            experimentService.update(factory.updateExperiment().apply(experimentService.find(id).orElseThrow(), experiment));
            String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
            return viewId + "?faces-redirect=true&includeViewParams=true";
        } catch (EJBException ex) {
            if (ex.getCause() instanceof OptimisticLockException) {
                facesContext.addMessage(null, new FacesMessage("Version conflict"));
            }
            return null;
        }
    }

}
