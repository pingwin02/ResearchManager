package lab.jee.experiment.view;

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

    private final ExperimentService service;

    private final ModelFunctionFactory factory;

    private ExperimentsModel experiments;

    @Inject
    public ExperimentList(ExperimentService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    public ExperimentsModel getExperiments() {
        if (experiments == null) {
            experiments = factory.experimentsToModel().apply(service.findAll());
        }
        return experiments;
    }

    public String deleteAction(ExperimentsModel.Experiment experiment) {
        service.delete(experiment.getId());
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }
}
