package lab.jee.experiment.view;

import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lab.jee.component.ModelFunctionFactory;
import lab.jee.experiment.model.ExperimentsModel;
import lab.jee.experiment.service.ExperimentService;

import java.io.Serializable;

@ViewScoped
@Named
public class ExperimentList implements Serializable {

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

    public void deleteAction(ExperimentsModel.Experiment experiment) {
        service.delete(experiment.getId());
        experiments = null;
    }
}
