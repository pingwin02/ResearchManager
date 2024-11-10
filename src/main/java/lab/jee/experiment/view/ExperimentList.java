package lab.jee.experiment.view;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lab.jee.component.ModelFunctionFactory;
import lab.jee.experiment.model.ExperimentsModel;
import lab.jee.experiment.service.ExperimentService;

@RequestScoped
@Named
public class ExperimentList {

    private final ModelFunctionFactory factory;
    private ExperimentService service;
    private ExperimentsModel experiments;

    @Inject
    public ExperimentList(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setService(ExperimentService service) {
        this.service = service;
    }

    public ExperimentsModel getExperiments() {
        if (experiments == null) {
            experiments = factory.experimentsToModel().apply(service.findAllForCallerPrincipal());
        }
        return experiments;
    }

    public String deleteAction(ExperimentsModel.Experiment experiment) {
        service.delete(experiment.getId());
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }
}
